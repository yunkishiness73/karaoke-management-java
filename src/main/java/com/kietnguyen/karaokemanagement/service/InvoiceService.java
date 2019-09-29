package com.kietnguyen.karaokemanagement.service;

import static com.kietnguyen.karaokemanagement.service.specification.DetailInvoiceSpecifications.hasInvoice;
import static com.kietnguyen.karaokemanagement.service.specification.DetailInvoiceSpecifications.hasItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.awt.GraphicsEnvironment;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument; 
import com.itextpdf.kernel.pdf.PdfWriter; 
//import com.itextpdf.text.*;
import com.itextpdf.layout.Document;
//import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.pdf.BaseFont;
import com.kietnguyen.karaokemanagement.model.DetailInvoice;
import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.repository.InvoiceRepository;
import com.kietnguyen.karaokemanagement.util.DateTimeUtil;

import static com.kietnguyen.karaokemanagement.service.specification.InvoiceSpecifications.hasTotalPrice;



@Service
public class InvoiceService {
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	public static final String FONT = "src/main/resources/fonts/DejaVuSerif.ttf";
	
	public List<Invoice> query(Integer totalPrice) {
		return invoiceRepository.findAll(hasTotalPrice(totalPrice));
	}
	
	public Integer getServiceCharge(Set<DetailInvoice> set) {
		if (set == null) return 0;
		
		ArrayList<DetailInvoice> detailInvoices = new ArrayList<>(set);
		Integer serviceCharge = 0;
	
		for (int i = 0; i < detailInvoices.size(); i++) {
			serviceCharge += detailInvoices.get(i).getPrice();
		}
		
		return serviceCharge;
	}
	
	public void printBill(Invoice invoice) {
//		Document document = new Document();
		
		try {
			PdfDocument pdfDoc = new PdfDocument(new PdfWriter("E:\\Programing\\Web Co Ban & Nang Cao\\Java\\eclipse-workspace\\karaokemanagement\\src\\main\\java\\com\\kietnguyen\\karaokemanagement\\itext.pdf"));
			 pdfDoc.setDefaultPageSize(PageSize.A5);
			Document document = new Document(pdfDoc);
		
//			PdfWriter.getInstance(document, new FileOutputStream(new File("E:\\Programing\\Web Co Ban & Nang Cao\\Java\\eclipse-workspace\\karaokemanagement\\src\\main\\java\\com\\kietnguyen\\karaokemanagement\\itext.pdf")));
		
			PdfFont baseFont = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, true);
//			BaseFont baseFont = BaseFont.createFont("fonts/ttf/dejavu/DejaVuSans.ttf", "cp1251", BaseFont.EMBEDDED);
	 
			
			Paragraph p = new Paragraph();
	        p.setTextAlignment(TextAlignment.CENTER);
	        p.add(new Text("Karaoke Idol").setFont(baseFont).setFontSize(23));
	        p.add(new Text("\n"));
	        p.add(new Text("HÓA ĐƠN TÍNH TIỀN").setFont(baseFont).setFontSize(18));
	   
	        document.add(p);
	        
	        float [] pointColumnWidths = {200F, 200F, 200F, 200F};
	        Table table = new Table(pointColumnWidths);
	        
	        Cell c1 = new Cell();
	        c1.add("Ngày");
	        c1.setFont(baseFont);
	        c1.setFontSize(12);
	        c1.setBorder(Border.NO_BORDER);             
	        c1.setTextAlignment(TextAlignment.LEFT);   
	        table.addCell(c1); 
	        
	        Cell c2 = new Cell();
	        c2.add(new SimpleDateFormat("dd/MM/yyyy").format(DateTimeUtil.getInstance().LocalDateTime2Date(invoice.getCheckOut())));
	        c2.setBorder(Border.NO_BORDER);             
	        c2.setTextAlignment(TextAlignment.LEFT);   
	        table.addCell(c2); 
	        
	        Cell c3 = new Cell();
	        c3.add("Phòng: ");
	        c3.setFont(baseFont);
	        c3.setFontSize(12);
	        c3.setBorder(Border.NO_BORDER);             
	        c3.setTextAlignment(TextAlignment.LEFT);   
	        table.addCell(c3); 
	        
	        Cell c4 = new Cell();
	        c4.add(invoice.getRoom().getName());
	        c4.setBorder(Border.NO_BORDER);             
	        c4.setTextAlignment(TextAlignment.LEFT);  
	        table.addCell(c4); 
	        
	        Cell c5 = new Cell();
	        c5.add("Giờ vào: ");
	        c5.setFont(baseFont);
	        c5.setFontSize(12);
	        c5.setBorder(Border.NO_BORDER);             
	        c5.setTextAlignment(TextAlignment.LEFT);   
	        table.addCell(c5); 
	        
	        Cell c6 = new Cell();
	        c6.add(new SimpleDateFormat("HH:mm").format(DateTimeUtil.getInstance().LocalDateTime2Date(invoice.getCheckIn())));
	        c6.setFont(baseFont);
	        c6.setFontSize(12);
	        c6.setBorder(Border.NO_BORDER);             
	        c6.setTextAlignment(TextAlignment.LEFT);   
	        table.addCell(c6); 
	        
	        Cell c7 = new Cell(1,2);
	        c7.setBorder(Border.NO_BORDER);             
	        table.addCell(c7); 
	        
//	        Cell c8 = new Cell();
////	        c7.add(new SimpleDateFormat("HH:mm").format(DateTimeUtil.getInstance().LocalDateTime2Date(invoice.getCheckIn())));
////	        c7.setFont(baseFont);
////	        c7.setFontSize(12);
//	        c7.setBorder(Border.NO_BORDER);             
////	        c7.setTextAlignment(TextAlignment.LEFT);   
//	        table.addCell(c8); 
	        
	        Cell c9 = new Cell();
	        c9.add("Giờ ra: ");
	        c9.setFont(baseFont);
	        c9.setFontSize(12);
	        c9.setBorder(Border.NO_BORDER);             
	        c9.setTextAlignment(TextAlignment.LEFT);   
	        table.addCell(c9); 
	        
	        Cell c10 = new Cell();
	        c10.add(new SimpleDateFormat("HH:mm").format(DateTimeUtil.getInstance().LocalDateTime2Date(invoice.getCheckOut())));
	        c10.setFont(baseFont);
	        c10.setFontSize(12);
	        c10.setBorder(Border.NO_BORDER);             
	        c10.setTextAlignment(TextAlignment.LEFT);   
	        table.addCell(c10); 
	        
	        Cell c11 = new Cell(1,2);
	        c11.setBorder(Border.NO_BORDER);             
	        table.addCell(c11); 
	        
	        Cell c12 = new Cell();
	        c12.add("Số giờ hát:");
	        c12.setFont(baseFont);
	        c12.setFontSize(12);
	        c12.setBorder(Border.NO_BORDER);             
	        c12.setTextAlignment(TextAlignment.LEFT);   
	        table.addCell(c12);
	        
	        Date d1 = DateTimeUtil.getInstance().LocalDateTime2Date(invoice.getCheckIn());
			Date d2 =  DateTimeUtil.getInstance().LocalDateTime2Date(invoice.getCheckOut());
			long milliSeconds = DateTimeUtil.getInstance().getMilliSecondsBetween2Days(d1, d2);
			int seconds = DateTimeUtil.getInstance().getSecond(milliSeconds);
			int hours = seconds / 3600;
			int minutes = (seconds % 3600) / 60;
	        
	        Cell c14 = new Cell();
	        c14.add(hours + ":" + minutes);
	        c14.setFont(baseFont);
	        c14.setFontSize(12);
	        c14.setBorder(Border.NO_BORDER);             
	        c14.setTextAlignment(TextAlignment.LEFT);   
	        table.addCell(c14);
	        
	        Cell c15 = new Cell(1,2);
	        c15.setBorder(Border.NO_BORDER);             
	        table.addCell(c15); 
	        
	        Cell c16 = new Cell();
	        c16.add("Tên");
	        c16.setFont(baseFont);
	        c16.setFontSize(12);
	        c16.setBorder(Border.NO_BORDER);             
	        c16.setTextAlignment(TextAlignment.CENTER);   
	        table.addCell(c16);
	        
	        Cell c17 = new Cell();
	        c17.add("Số lượng");
	        c17.setFont(baseFont);
	        c17.setFontSize(12);
	        c17.setBorder(Border.NO_BORDER);             
	        c17.setTextAlignment(TextAlignment.CENTER);   
	        table.addCell(c17);
	        
	        Cell c18 = new Cell();
	        c18.add("Giá");
	        c18.setFont(baseFont);
	        c18.setFontSize(12);
	        c18.setBorder(Border.NO_BORDER);             
	        c18.setTextAlignment(TextAlignment.CENTER);   
	        table.addCell(c18);
	        
	        Cell c19 = new Cell();
	        c19.add("Tổng");
	        c19.setFont(baseFont);
	        c19.setFontSize(12);
	        c19.setBorder(Border.NO_BORDER);             
	        c19.setTextAlignment(TextAlignment.CENTER);   
	        table.addCell(c19);
	        
	        Cell c20 = new Cell(1,4);
	        c20.add("-------------------------------------------------------------------------------------");
	        c20.setHeight(35);
	        c20.setBorder(Border.NO_BORDER);             
	        table.addCell(c20);
	        
	        //Generate cells for item list
	        ArrayList<DetailInvoice> detailInvoices = new ArrayList<>(invoice.getDetailInvoices());
	        for (int i = 0; i < detailInvoices.size(); i++) {
	        	DetailInvoice item = detailInvoices.get(i);
	        	Cell col1 = new Cell(1,2);
	        	col1.add(item.getItem().getName());
	        	col1.setFont(baseFont);
	        	col1.setFontSize(12);
	        	col1.setBorder(Border.NO_BORDER);             
	        	col1.setTextAlignment(TextAlignment.LEFT);   
		        table.addCell(col1);
		        
		        Cell col2 = new Cell();
		        col2.add(item.getQuantity() + "");
		        col2.setFont(baseFont);
		        col2.setFontSize(12);
		        col2.setBorder(Border.NO_BORDER);             
		        col2.setTextAlignment(TextAlignment.CENTER);   
		        table.addCell(col2);
		       
		        Cell col3 = new Cell();
		        col3.add(item.getItem().getPrice() + "");
		        col3.setFont(baseFont);
		        col3.setFontSize(12);
		        col3.setBorder(Border.NO_BORDER);             
		        col3.setTextAlignment(TextAlignment.CENTER);   
		        table.addCell(col3);
		        
		        Cell col4 = new Cell();
		        col4.add(item.getPrice() + "");
		        col4.setFont(baseFont);
		        col4.setFontSize(12);
		        col4.setBorder(Border.NO_BORDER);             
		        col4.setTextAlignment(TextAlignment.CENTER);   
	        	table.addCell(col4);
	        }
	
	        
	        document.add(table);
	        
	        document.close();  
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
