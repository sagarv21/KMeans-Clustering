import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class SimilarMovies {

	public void findSimilarMovies(Long runTime, Long rating, String[] genere)
	{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("D:\\DataMiningProject\\Project3\\centers.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String currentLine;
		String[] temp= {Long.toString(runTime),Long.toString(rating),"0","0","0","0","0","0","0"};
		for(int i=0;i<genere.length;i++)
		{
			if(genere[i].equalsIgnoreCase("Action & Adventure"))
			{
				temp[2] = "1";
			}
			if(genere[i].equalsIgnoreCase("Animation"))
			{
				temp[3] = "1";
			}
			if(genere[i].equalsIgnoreCase("Classics"))
			{
				temp[5] = "1";
			}
			if(genere[i].equalsIgnoreCase("Comedy"))
			{
				temp[4] = "1";
			}
			if(genere[i].equalsIgnoreCase("Drama"))
			{
				temp[5] = "1";
			}
			if(genere[i].equalsIgnoreCase("Horror"))
			{
				temp[2] = "1";
			}
			if(genere[i].equalsIgnoreCase("Mystery & Suspense"))
			{
				temp[2] = "1";
			}
			if(genere[i].equalsIgnoreCase("Romance"))
			{
				temp[7] = "1";
			}
			if(genere[i].equalsIgnoreCase("Documentary"))
			{
				temp[6] = "1";
			}
		}

    	double max_distance = -1;
    	int max_center = -1;
    	int count = 0;
		try {
		    while((currentLine = br.readLine()) != null)
		    {
		    	
		    	String centers[] = currentLine.split(",");
		    	double distance = 0;
		    	for(int p=0;p<temp.length;p++)
				{
					if(p==0)
					{
						distance = 1 / (.1 * Math.sqrt(Math.pow(Integer.parseInt(centers[p]) - Integer.parseInt(temp[0]),2)));
					}
					else if(p==1)
					{
						distance = distance + (1 / (.5 * Math.sqrt(Math.pow(Double.parseDouble(centers[p]) - Double.parseDouble(temp[1]),2))));
					}
					else if(p==2 && centers[p].equalsIgnoreCase(temp[p]))
					{
						distance = distance + 1000; //Action
					}
					else if(p==3 && centers[p].equalsIgnoreCase(temp[p]))
					{
						distance = distance + 3000; //Animation
					}
					else if(p==4 && centers[p].equalsIgnoreCase(temp[p]))
					{
						distance = distance + 200; //Comedy
					}
					else if(p==5 && centers[p].equalsIgnoreCase(temp[p]))
					{
						distance = distance + 300; //Drama
					}
					else if(p==6 && centers[p].equalsIgnoreCase(temp[p]))
					{
						distance = distance + 400; //Documentary
					}
					else if(p==7 && centers[p].equalsIgnoreCase(temp[p]))
					{
						distance = distance + 2000; //Romance
					}
					else if(p==8 && centers[p].equalsIgnoreCase(temp[p]))
					{
						distance = distance + 10; //Short
					}
					
				}
				if(max_center == -1)
				{
					max_distance = distance;
					max_center = count;
				}
				else if(distance > max_distance)
				{
					max_distance = distance;
					max_center = count;
				}
				count++;

		    }br.close();
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
		BufferedReader br1 = null;
		try {
			br1 = new BufferedReader(new FileReader("D:\\DataMiningProject\\Project3\\movieClustered.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int count1 =0;
		try {
		while((currentLine = br1.readLine()) != null)
		{
			if(count1 == count)
			{
				if(!currentLine.equals(""))
				{
					System.out.println(currentLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")[0]);
					System.out.println(currentLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")[1]);
				}
			}
		}
		br1.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
