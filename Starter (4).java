import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Starter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		boolean clustered = true;
		
		if(clustered == false)
		{
			Cluster c = new Cluster("D:\\DataMiningProject\\Project3\\movies_data.csv",1000);
			c.clusterFile();			
		}
		else
		{
		SentimentAnalyzer sa = new SentimentAnalyzer();
		HTTPRequest hr = new HTTPRequest();
		
		Scanner s = new Scanner(System.in);
		System.out.println("Enter a movie name : ");
		String movie = s.nextLine();
		
		String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=vyhu72pmc39ptd28vnyum22h&q=" + movie + "&page_limit=1";
		JSONObject movieObject = hr.sendGet(url);
		
		String movieID = (String)((JSONObject)((JSONArray) movieObject.get("movies")).get(0)).get("id");
		
		url = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + movieID + "/similar.json?limit=5&apikey=vyhu72pmc39ptd28vnyum22h";
		JSONObject similarObject = hr.sendGet(url);
		
		url = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + movieID + ".json?limit=5&apikey=vyhu72pmc39ptd28vnyum22h";
		JSONObject genereObject = hr.sendGet(url);
		
		JSONArray genereArray = (JSONArray) genereObject.get("genres");
		
		String[]  genere = new String[genereArray.size()];
		Long runTime = (Long) genereObject.get("runtime");
		Long rating = (Long)((JSONObject) genereObject.get("ratings")).get("audience_score");
		for(int i=0;i<genere.length;i++)
		{
			genere[i] = (String) genereArray.get(i);
		}
		System.out.println(rating);
		
		SimilarMovies sm = new SimilarMovies();
		
		
		System.out.println("Movie Name : " + (String)((JSONObject)((JSONArray) movieObject.get("movies")).get(0)).get("title"));
		System.out.println();
		System.out.println("Movies similar to this movie are : ");
		System.out.println();
	
		sm.findSimilarMovies(runTime, rating, genere);
		
		JSONArray otherMovies = ((JSONArray) similarObject.get("movies"));
		for(int i=0;i<otherMovies.size();i++)
		{
			String otherTitles = (String)((JSONObject)otherMovies.get(i)).get("title");
			System.out.println(otherTitles);
		}
		
		url = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + movieID + "/reviews.json?review_type=all&page_limit=20&page=1&country=us&apikey=vyhu72pmc39ptd28vnyum22h";
		JSONObject reviewObject = hr.sendGet(url);
		JSONArray reviews = ((JSONArray) reviewObject.get("reviews"));
		
		
		int count =0;
		String[] sentimentText = {"Very Negative","Negative", "Neutral", "Positive", "Very Positive"};
		ArrayList<Integer> sentimentCount = new ArrayList<Integer>();
		sentimentCount.add(0);
		sentimentCount.add(0);
		sentimentCount.add(0);
		sentimentCount.add(0);
		sentimentCount.add(0);
		for(int i=0;i<reviews.size();i++)
		{
			count ++;
			String review = (String)((JSONObject)reviews.get(i)).get("quote");
			int sentiment = sa.findSentiment(review);
			int value = sentimentCount.get(sentiment)+1;
			sentimentCount.remove(sentiment);
			sentimentCount.add(sentiment, value);
			if(count == 30)
				break;
		}
		int index = -1;
		int max = -1;
		for(int i=0;i<sentimentCount.size();i++)
		{
			if(sentimentCount.get(i) > max)
			{
				   max = sentimentCount.get(i);
				   index = i;
			}
		}
		System.out.println(sentimentText[index]);
		}
	}
}
