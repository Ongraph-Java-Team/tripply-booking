package com.tripply.booking.service.Impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.entity.Hotel;
import com.tripply.booking.entity.Room;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.response.RoomUploadResponse;
import com.tripply.booking.repository.HotelRepository;
import com.tripply.booking.repository.RoomRepository;
import com.tripply.booking.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Override
	public ResponseModel<List<RoomUploadResponse>> uploadRooms(UUID hotelId, MultipartFile file) throws Exception {
		Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
		if (!hotelOptional.isPresent()) {
	         throw new DataNotFoundException("Hotel not Found");
		}

		Hotel hotel = hotelOptional.get();
        List<Room> rooms = new ArrayList<>();
        List<RoomUploadResponse> responses = new ArrayList<>();

        String fileType = file.getContentType();
        if (fileType.equals("text/csv")) {
            rooms = parseCSV(file, responses);
        } else if (fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            rooms = parseExcel(file, responses);
        } else {
            throw new BadRequestException("Invalid file format");
        }
        for (Room room : rooms) {
            try {
				if (roomRepository.existsByHotelIdAndRoomNumber(hotelId, room.getRoomNumber())) {
					throw new BadRequestException("Room with number " + room.getRoomNumber() + " already exists in the hotel.");
				}
                room.setHotel(hotel);
                roomRepository.save(room);
                responses.add(new RoomUploadResponse(room.getRoomNumber(), "Success", "Room uploaded successfully."));
            } catch (Exception e) {
                responses.add(new RoomUploadResponse(room.getRoomNumber(), "Failure", "Failed to upload room: " + e.getMessage()));
            }
        }

        ResponseModel<List<RoomUploadResponse>> responseModel = new ResponseModel<>();
        responseModel.setData(responses);
        responseModel.setStatus(HttpStatus.OK); 
        responseModel.setMessage("Rooms uploaded"); 

        return responseModel;
	}

	private List<Room> parseCSV(MultipartFile file, List<RoomUploadResponse> responses) throws Exception {
		List<Room> rooms = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
				CSVReader csvReader = new CSVReader(br)) {
			String[] line;
			int lineNumber = 0;
			while ((line = csvReader.readNext()) != null) {
				lineNumber++;
				if (line[0].equals("room_number")) {
					continue;
				}
				try {
					Room room = new Room();
					room.setRoomNumber(line[0]);
					room.setCategory(line[1]);
					room.setType(line[2]);
					room.setPrice(Double.parseDouble(line[3]));
					room.setDescription(line[4]);
					room.setAmenities(Arrays.asList(line[5].split(";")));
					rooms.add(room);
				} catch (Exception e) {
					responses.add(new RoomUploadResponse(line[0], "Failure",
							"Failed to parse line " + lineNumber + ": " + e.getMessage()));
				}
			}
		}
		return rooms;
	}

	private List<Room> parseExcel(MultipartFile file, List<RoomUploadResponse> responses) throws Exception {
		List<Room> rooms = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			int rowNum = 0;
			for (Row row : sheet) {
				rowNum++;
				if (row.getRowNum() == 0) {
					continue;
				}
				try {
					Room room = new Room();
					room.setRoomNumber(row.getCell(0).getStringCellValue());
					room.setCategory(row.getCell(1).getStringCellValue());
					room.setType(row.getCell(2).getStringCellValue());
					room.setPrice(row.getCell(3).getNumericCellValue());
					room.setDescription(row.getCell(4).getStringCellValue());
					room.setAmenities(Arrays.asList(row.getCell(5).getStringCellValue().split(";")));
					rooms.add(room);
				} catch (Exception e) {
					responses.add(new RoomUploadResponse(row.getCell(0).getStringCellValue(), "Failure",
							"Failed to parse row " + rowNum + ": " + e.getMessage()));
				}
			}
		}
		return rooms;
	}
}
