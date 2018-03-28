import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Cluster {
	
	String file;
	int k;
	public Cluster(String file, int k)
	{
		this.file = file;
		this.k = k;
	}

	public void clusterFile()
	{
		HashMap<String,String> movieCluster = new HashMap<String,String>();
		BufferedReader br = null;
		List<String[]> inputData = new ArrayList<String[]>();
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String currentLine;
		String header = " ";
		try {
			header = br.readLine();
		    while((currentLine = br.readLine()) != null)
		    {
		    	String[] line = currentLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		    	inputData.add(line);
		    	//System.out.println(currentLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")[1]);
	        }
		    System.out.println(inputData.size());
		br.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int m = inputData.size();
		int n = inputData.get(0).length;
		ArrayList<String[]> centers = new ArrayList<String[]>();
		
		int[] index = new int[k];
		Writer writer = null;

		try {
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\DataMiningProject\\Project3\\centers.csv"), "utf-8"));
		} catch (IOException ex) {
		  // report
		} 
		for(int i =0;i<k;i++)
		{
			index[i] = (int)Math.floor((i+1) * (double)m/k);
			String[] center = {inputData.get(index[i]-1)[3],inputData.get(index[i]-1)[5],inputData.get(index[i]-1)[28],inputData.get(index[i]-1)[29],inputData.get(index[i]-1)[30],inputData.get(index[i]-1)[31],inputData.get(index[i]-1)[31],inputData.get(index[i]-1)[32],inputData.get(index[i]-1)[33],inputData.get(index[i]-1)[34]};
			centers.add(center);
			try {
				writer.write(inputData.get(index[i]-1)[3] + "," + inputData.get(index[i]-1)[5] + "," + inputData.get(index[i]-1)[28] + "," + inputData.get(index[i]-1)[29] + "," + inputData.get(index[i]-1)[30] + "," + inputData.get(index[i]-1)[31] + "," + inputData.get(index[i]-1)[31] + "," + inputData.get(index[i]-1)[32] + "," + inputData.get(index[i]-1)[33] + "," + inputData.get(index[i]-1)[34]);
				writer.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	    try {writer.close();} catch (Exception ex) {}
			
		
		int[] idx = new int[m];
		for(int i=0;i<m;i++)
		{
			idx[i] = 0;
		}
		
		int iter = 0;
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the number of iterations : ");
		int max_iter = Integer.parseInt(scan.nextLine());
	
		while(true)
		{
		
			int[] old_idx = new int[idx.length];
			for(int i=0;i<old_idx.length;i++)
			{
				old_idx[i] = idx[i];
			}
			for(int i=0;i<m;i++)
			{
				System.out.println(i);
				String[] temp = {inputData.get(i)[3],inputData.get(i)[5],inputData.get(i)[28],inputData.get(i)[29],inputData.get(i)[30],inputData.get(i)[31],inputData.get(i)[31],inputData.get(i)[32],inputData.get(i)[33],inputData.get(i)[34]};
				int max_center = -1;
				double max_distance = -1;
				for(int j=0;j<k;j++)
				{
					double distance = 0;
					
					for(int p=0;p<temp.length;p++)
					{
						if(p==0)
						{
							distance = 1 / (.1 * Math.sqrt(Math.pow(Integer.parseInt(centers.get(j)[p]) - Integer.parseInt(temp[0]),2)));
						}
						else if(p==1)
						{
							distance = distance + (1 / (.5 * Math.sqrt(Math.pow(Double.parseDouble(centers.get(j)[p]) - Double.parseDouble(temp[1]),2))));
						}
						else if(p==2 && centers.get(j)[p].equalsIgnoreCase(temp[p]))
						{
							distance = distance + 1000; //Action
						}
						else if(p==3 && centers.get(j)[p].equalsIgnoreCase(temp[p]))
						{
							distance = distance + 3000; //Animation
						}
						else if(p==4 && centers.get(j)[p].equalsIgnoreCase(temp[p]))
						{
							distance = distance + 200; //Comedy
						}
						else if(p==5 && centers.get(j)[p].equalsIgnoreCase(temp[p]))
						{
							distance = distance + 300; //Drama
						}
						else if(p==6 && centers.get(j)[p].equalsIgnoreCase(temp[p]))
						{
							distance = distance + 400; //Documentary
						}
						else if(p==7 && centers.get(j)[p].equalsIgnoreCase(temp[p]))
						{
							distance = distance + 2000; //Romance
						}
						else if(p==8 && centers.get(j)[p].equalsIgnoreCase(temp[p]))
						{
							distance = distance + 10; //Short
						}
						
					}
					if(max_center == -1)
					{
						max_distance = distance;
						max_center = j;
					}
					else if(distance > max_distance)
					{
						max_distance = distance;
						max_center = j;
					}
					
				}
				idx[i] = max_center;
			}
			break;
		}
		Writer writer1 = null;

		try {
		    writer1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\DataMiningProject\\Project3\\movieClustered.csv"), "utf-8"));
		} catch (IOException ex) {
		  // report
		} 
		for(int i=0;i<k;i++)
		{
			String movieList = "";
			for(int j =0;j<m;j++)
			{
				if(idx[j] == i)
				{
					movieList = movieList + "," + inputData.get(j)[1];
				}
			}
			try {
				writer1.write(movieList);
				writer1.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		try {writer1.close();} catch (Exception ex) {}

}
}
