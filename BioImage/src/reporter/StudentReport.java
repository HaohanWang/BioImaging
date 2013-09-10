package reporter;

import synchronization.Consumer;
import synchronization.SynchronizedBuffer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import objectModel.Report;
import objectModel.SignalNode;
import objectModel.SignalSequence;
import objectModel.Student;
import objectModel.Tutorial;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class StudentReport extends Report{
	
	protected StudentReport(Tutorial tutorial, Student user){
		super();
		this.setTutorial(tutorial);
		this.setLearner(user);
	}
	
	protected StudentReport(Tutorial tutorial, Student user, SignalSequence sigal){
		
	}
	
	public static void main(String[] args) throws IOException, Exception {
		ArrayList<SignalNode> data = new ArrayList<SignalNode>();
		ArrayList<String> lecres = new ArrayList<String>();
		String path = "model/newLabeledData.txt";
		String line = null;

		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		while ((line = br.readLine()) != null) {
			SignalNode temp = new SignalNode();
			String[] temp1 = line.split(",");
			/******************* set data ******************/
			temp.setTimestamp(Long.parseLong(temp1[0]));
			temp.setConcentration(Double.parseDouble(temp1[3]));
			temp.setMeditation(Double.parseDouble(temp1[4]));
			temp.setRaw(Double.parseDouble(temp1[5]));
			temp.setTheta(Double.parseDouble(temp1[6]));
			temp.setDelta(Double.parseDouble(temp1[7]));
			temp.setAlpha1(Double.parseDouble(temp1[8]));
			temp.setAlpha2(Double.parseDouble(temp1[9]));
			temp.setBeta1(Double.parseDouble(temp1[10]));
			temp.setBeta2(Double.parseDouble(temp1[11]));
			temp.setGamma1(Double.parseDouble(temp1[12]));
			temp.setGamma2(Double.parseDouble(temp1[13]));
			temp.setConfusion(Integer.parseInt(temp1[14]));
			data.add(temp);
		}
		lecres = Reporter(data);
		createPdf("model/reporter.pdf", lecres);
		System.out.println(dealtime(2222));
	}

	static ArrayList<String> Reporter(ArrayList<SignalNode> data) {
		long start = 0, end = 0;
		long sum = 0, interval = 0;
		int flag = 0;
		String result = null;
		ArrayList<String> stringlist = new ArrayList<String>();
		for (int i = 1; i < data.size(); i++) {
			if (data.get(i).getConfusion() == 1 && flag == 0) {
				start = data.get(i).getTimestamp();
				flag = 1;
			} else if (data.get(i).getConfusion() == 0 && flag == 1) {
				end = data.get(i - 1).getTimestamp();
				interval = end - start;
				sum += interval;
				result = "From" + " " + dealtime(start) + " To" + " "
						+ dealtime(end) + " Confused";
				stringlist.add(result);
				start = 0;
				end = 0;
				flag = 0;
			}
		}
		stringlist.add("TOTAL Confusion Time is: " + dealtime(sum));
		return stringlist;
	}

	public static String dealtime(long timestamp) {
		String time = "";
		long sec = timestamp / 1000;

		int hrs, min = 0;
		hrs = (int) (sec / (60 * 60));
		time += hrs + "h";

		min = (int) ((sec - hrs * 3600) / 60);
		time += min + "m";

		sec = sec - hrs * 3600 - min * 60;
		time += sec + "s";

		return time;
	}

	@SuppressWarnings("deprecation")
	public static void createPdf(String filename, ArrayList<String> result)
			throws DocumentException, IOException {

		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		Font titlestyle = new Font(Font.FontFamily.COURIER, 32, Font.BOLD,
				BaseColor.RED);

		Font bodystyle = new Font(Font.FontFamily.HELVETICA, 28, Font.ITALIC);

		Font bodystyle2 = new Font(Font.FontFamily.TIMES_ROMAN, 23,
				Font.NORMAL, BaseColor.GRAY);
		Font sum = new Font(Font.FontFamily.HELVETICA, 30, Font.UNDERLINE,
				BaseColor.BLUE);
		Paragraph title = new Paragraph("CONGRAUTULATIONS! YOU FINISHED",
				titlestyle);
		title.setAlignment(1);
		Paragraph body1 = new Paragraph("YOUR REPORT: ", bodystyle);

		FileOutputStream output = new FileOutputStream(filename);
		PdfWriter.getInstance(document, output);

		document.open();
		Paragraph para = new Paragraph("", bodystyle2);
		Paragraph total = new Paragraph("", sum);
		int size = result.size();
		for (int i = 0; i < size - 1; i++) {
			para.add(result.get(i));
			para.add(Chunk.NEWLINE);
		}

		document.add(title);
		body1.add(Chunk.NEWLINE);
		body1.add(Chunk.NEWLINE);
		document.add(body1);
		document.add(para);
		total.add(result.get(size - 1));
		document.add(total);
		Image tImgCover = Image.getInstance("model/" + "test.jpg");

		tImgCover.setAbsolutePosition(0, 0);

		tImgCover.scaleAbsolute(595, 842);
		document.add(tImgCover);
		document.close();
		output.close();
	}

}
