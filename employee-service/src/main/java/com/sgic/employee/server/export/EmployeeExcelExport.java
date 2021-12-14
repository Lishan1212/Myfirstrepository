package com.sgic.employee.server.export;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sgic.employee.server.entities.Employee;

public class EmployeeExcelExport {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private List<Employee> listEmployee;

	public EmployeeExcelExport(List<Employee> listEmployee) {
		this.listEmployee = listEmployee;
		workbook = new XSSFWorkbook();
	}

	private void createCell(Row row, int columnCount, Object value, CellStyle cellStyle) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(cellStyle);
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("EmployeeDetails");

		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(20);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		createCell(row, 0, "Employee Information", style);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		font.setFontHeightInPoints((short) (10));

		row = sheet.createRow(1);
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "Id", style);
		createCell(row, 1, "Email", style);
		createCell(row, 2, "Designation", style);
		createCell(row, 3, "FirstName", style);	
		createCell(row, 4, "Gender", style);
		createCell(row, 5, "LastName", style);
		createCell(row, 6, "MobileNumber", style);

	}

	private void writeDataLines() {
		int rowCount = 2;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (Employee employee : listEmployee) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, employee.getEmployeeId(), style);
			createCell(row, columnCount++, employee.getEmailId(), style);
			createCell(row, columnCount++, employee.getDesignation().getDesignationName(), style);
			createCell(row, columnCount++, employee.getFirstName(), style);
			createCell(row, columnCount++, employee.getGender(), style);
			createCell(row, columnCount++, employee.getLastName(), style);
			createCell(row, columnCount++, employee.getPhoneNo(), style);
			
		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

}
