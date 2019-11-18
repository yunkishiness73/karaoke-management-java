package com.kietnguyen.karaokemanagement.service;

import static com.kietnguyen.karaokemanagement.service.specification.InvoiceSpecifications.equalTo;
import static com.kietnguyen.karaokemanagement.service.specification.InvoiceSpecifications.hasTotalPrice;
import static com.kietnguyen.karaokemanagement.service.specification.InvoiceSpecifications.like;
import static com.kietnguyen.karaokemanagement.service.specification.InvoiceSpecifications.isPaid;
import static com.kietnguyen.karaokemanagement.service.specification.InvoiceSpecifications.hasRoomId;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.kietnguyen.karaokemanagement.model.DetailInvoice;
import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Revenue;
import com.kietnguyen.karaokemanagement.repository.InvoiceRepository;
import com.kietnguyen.karaokemanagement.util.DateTimeUtil;

@Service
public class InvoiceService {
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	public static final String FONT = "src/main/resources/fonts/DejaVuSerif.ttf";
	public static final String DAY = "day";
	public static final String MONTH = "month";
	public static final String YEAR = "year";
	
	public List<Invoice> findAll() {
		return invoiceRepository.findAll(isPaid(true));
	}
	
	public List<Invoice> search(String datepicker) {
		return invoiceRepository.findAll(equalTo(datepicker).and(isPaid(true)));
	}
	
	public List<Invoice> populateCriteriaSearch(String keyword) {
		System.out.println(keyword);
		return invoiceRepository.findAll(like(keyword).and(isPaid(true)));
	}
	
	public List<Invoice> populateOptionsSearch(String keyword, String datepicker) {
		return invoiceRepository.findAll(Specification.where(equalTo(datepicker)).and(like(keyword)).and(isPaid(true)));
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
	
	public String formatNumber(Locale locale, Integer number) {
		 NumberFormat numberFormat = NumberFormat.getInstance(locale);
		 
		 return numberFormat.format(number);
	}
	
	public List<Revenue> getRevenue(String from, String to, String viewType) {
		switch (viewType) {
			case DAY:
				return invoiceRepository.getRevenueByDay(from, to);
			case MONTH:
				return invoiceRepository.getRevenueByMonth(from, to);
			case YEAR:	
				return invoiceRepository.getRevenueByYear(from, to);
			default:
				return null;
		}
	}
	
	public Invoice findInvoiceByRoomId(Integer roomId) {
		List<Invoice> invoices = invoiceRepository.findAll(hasRoomId(roomId));
	
		if (invoices.size() == 0)
			return null;
		
		return invoices.get(0);
	}
	
	public boolean printBill(Invoice invoice, Integer charge) {
		try {
			String base_path = "src/main/resources/public/";
			String invoice_pdf =  "bills/Bill_" + invoice.getRoom().getName() + "_" + invoice.getId() + ".pdf";
			base_path += invoice_pdf;
			PdfDocument pdfDoc = new PdfDocument(new PdfWriter(base_path));
			 pdfDoc.setDefaultPageSize(PageSize.A5);
			Document document = new Document(pdfDoc);
		
			PdfFont baseFont = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, true);
	 
			
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
	        
	        float [] pointColumnWidths2 = {200F, 200F, 200F, 200F, 200F};
	        Table table2 = new Table(pointColumnWidths2);
	        
	        Cell c16 = new Cell(1,2);
	        c16.add("Tên");
	        c16.setFont(baseFont);
	        c16.setFontSize(12);
	        c16.setBorder(Border.NO_BORDER);             
	        c16.setTextAlignment(TextAlignment.CENTER);   
	        table2.addCell(c16);
	        
	        Cell c17 = new Cell();
	        c17.add("Số lượng");
	        c17.setFont(baseFont);
	        c17.setFontSize(12);
	        c17.setBorder(Border.NO_BORDER);             
	        c17.setTextAlignment(TextAlignment.CENTER);   
	        table2.addCell(c17);
	        
	        Cell c18 = new Cell();
	        c18.add("Giá");
	        c18.setFont(baseFont);
	        c18.setFontSize(12);
	        c18.setBorder(Border.NO_BORDER);             
	        c18.setTextAlignment(TextAlignment.CENTER);   
	        table2.addCell(c18);
	        
	        Cell c19 = new Cell();
	        c19.add("Tổng");
	        c19.setFont(baseFont);
	        c19.setFontSize(12);
	        c19.setBorder(Border.NO_BORDER);             
	        c19.setTextAlignment(TextAlignment.CENTER);   
	        table2.addCell(c19);
	        
	        Cell c20 = new Cell(1,5);
	        c20.add("-------------------------------------------------------------------------------------");
	        c20.setHeight(20);
	        c20.setBorder(Border.NO_BORDER);             
	        table2.addCell(c20);
	        
	        //Generate cells for item list
	        ArrayList<DetailInvoice> detailInvoices = new ArrayList<>(invoice.getDetailInvoices());
	        Integer serviceCharge = 0;
	        for (int i = 0; i < detailInvoices.size(); i++) {
	        	DetailInvoice item = detailInvoices.get(i);
	        	
	        	serviceCharge += item.getPrice();
	        	
	        	Cell col1 = new Cell(1,2);
	        	col1.add(item.getItem().getName());
	        	col1.setFont(baseFont);
	        	col1.setFontSize(12);
	        	col1.setBorder(Border.NO_BORDER);             
	        	col1.setTextAlignment(TextAlignment.LEFT);   
	        	table2.addCell(col1);
		        
		        Cell col2 = new Cell();
		        col2.add(item.getQuantity() + "");
		        col2.setFontSize(12);
		        col2.setBorder(Border.NO_BORDER);             
		        col2.setTextAlignment(TextAlignment.CENTER);   
		        table2.addCell(col2);
		       
		        Cell col3 = new Cell();
		        col3.add(this.formatNumber(Locale.getDefault(), item.getItem().getPrice()));
		        col3.setFontSize(12);
		        col3.setBorder(Border.NO_BORDER);             
		        col3.setTextAlignment(TextAlignment.CENTER);   
		        table2.addCell(col3);
		        
		        Cell col4 = new Cell();
		        col4.add(this.formatNumber(Locale.getDefault(), item.getPrice()));
		        col4.setFontSize(12);
		        col4.setBorder(Border.NO_BORDER);             
		        col4.setTextAlignment(TextAlignment.CENTER);   
		        table2.addCell(col4);
	        }
	        
	        Cell c21 = new Cell(1,5);
	        c21.add("-------------------------------------------------------------------------------------");
	        c21.setHeight(20);
	        c21.setBorder(Border.NO_BORDER);             
	        table2.addCell(c21);
	        
	        Table table3 = new Table(pointColumnWidths);
	        
	        Cell c24 = new Cell(1,2);
	        c24.add("Dịch vụ: ");
	        c24.setFont(baseFont);
	        c24.setFontSize(12);
	        c24.setBorder(Border.NO_BORDER);             
	        table3.addCell(c24);
	        
	        Cell c25 = new Cell(1,2);
	        c25.setFont(baseFont);
	        c25.add(this.formatNumber(Locale.getDefault(), serviceCharge) + " đồng");
	        c25.setBorder(Border.NO_BORDER);             
	        table3.addCell(c25);
	        
	        Cell c22 = new Cell(1,2);
	        c22.add("Phụ thu: ");
	        c22.setFont(baseFont);
	        c22.setFontSize(12);
	        c22.setBorder(Border.NO_BORDER);             
	        table3.addCell(c22);
	        
	        Cell c23 = new Cell(1,2);
	        c23.setFont(baseFont);
	        c23.add(this.formatNumber(Locale.getDefault(), invoice.getSurcharge()) + " đồng");
	        c23.setBorder(Border.NO_BORDER);             
	        table3.addCell(c23);
	        
	        Cell c28 = new Cell(1,2);
	        c28.add("Tiền giờ: ");
	        c28.setFont(baseFont);
	        c28.setFontSize(12);
	        c28.setBorder(Border.NO_BORDER);             
	        table3.addCell(c28);
	        
	        Cell c29 = new Cell(1,2);
	        c29.setFont(baseFont);
	        c29.add(this.formatNumber(Locale.getDefault(), invoice.getTotalPrice()-invoice.getSurcharge()-serviceCharge) + " đồng");
	        c29.setBorder(Border.NO_BORDER);             
	        table3.addCell(c29);
	        
	        Cell c31 = new Cell(1,2);
	        c31.add("Tổng hóa đơn: ");
	        c31.setFont(baseFont);
	        c31.setFontSize(16);
	        c31.setBorder(Border.NO_BORDER);             
	        table3.addCell(c31);
	        
	        Cell c32 = new Cell(1,2);
	        c32.setFontSize(16);
	        c32.setFont(baseFont);
	        c32.add(this.formatNumber(Locale.getDefault(), invoice.getTotalPrice()) + " đồng");
	        c32.setBorder(Border.NO_BORDER);             
	        table3.addCell(c32);
	        
	        Cell c34 = new Cell(1,4);
	        c34.add("-------------------------------------------------------------------------------------");
	        c34.setHeight(20);
	        c34.setBorder(Border.NO_BORDER);             
	        table3.addCell(c34);
	        
	        Cell c35 = new Cell(1,2);
	        c35.add("Tiền khách đưa: ");
	        c35.setFont(baseFont);
	        c35.setFontSize(12);
	        c35.setBorder(Border.NO_BORDER);             
	        table3.addCell(c35);
	        
	        Cell c36 = new Cell(1,2);
	        c36.setFontSize(12);
	        c36.setFont(baseFont);
	        c36.add(this.formatNumber(Locale.getDefault(), charge) + " đồng");
	        c36.setBorder(Border.NO_BORDER);             
	        table3.addCell(c36);
	        
	        Cell c38 = new Cell(1,2);
	        c38.add("Tiền trả khách: ");
	        c38.setFont(baseFont);
	        c38.setFontSize(12);
	        c38.setBorder(Border.NO_BORDER);             
	        table3.addCell(c38);
	        
	        Cell c39 = new Cell(1,2);
	        c39.setFontSize(12);
	        c39.setFont(baseFont);
	        c39.add(this.formatNumber(Locale.getDefault(), charge - invoice.getTotalPrice()) + " đồng");
	        c39.setBorder(Border.NO_BORDER);             
	        table3.addCell(c39);
	        
	        Cell c41 = new Cell(1,2);
	        c41.add("* Tiền giờ: ");
	        c41.setFont(baseFont);
	        c41.setFontSize(12);
	        c41.setHeight(40);
	        c41.setBorder(Border.NO_BORDER);             
	        table3.addCell(c41);
	        
	        Cell c42 = new Cell(1,2);
	        c42.setFontSize(12);
	        c42.setFont(baseFont);
	        c42.setHeight(40);
	        c42.add(this.formatNumber(Locale.getDefault(), invoice.getRoom().getRoomType().getEvent().getBasePrice()) + " đồng/giờ");
	        c42.setBorder(Border.NO_BORDER);             
	        table3.addCell(c42);
	
	        
	        document.add(table);
	        document.add(table2);
	        document.add(table3);
	        
	        document.close();
	        pdfDoc.close();
	        
	        //when we issue an invoice, we also update invoice's status is paid
	        invoice.setInvoicePdf(invoice_pdf);
	        invoice.setIsPaid(true);
	        invoice.getRoom().setIsBooking(false);
	        
	        invoiceRepository.save(invoice);
	        
	        return true;
			
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
