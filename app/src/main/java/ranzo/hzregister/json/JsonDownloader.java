package ranzo.hzregister.json;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class JsonDownloader extends AsyncTask<String, String, String> {

	private final String URL = "https://enterprise.hanzo.com.br/contents/8/fields.json";
	
	private OnTaskCompleted listener;
	private ProgressDialog progressDialog;
	
	public JsonDownloader(ProgressDialog progressDialog, OnTaskCompleted listener ){
		this.listener = listener;
		this.progressDialog = progressDialog;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				progressDialog.show();
			}
		});

	}
	
	@Override
	protected String doInBackground(String... params) {
		String json = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(URL);
			HttpResponse response = client.execute(post);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == HttpStatus.SC_OK ){
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				Scanner scanner = new Scanner(content);
				json = scanner.useDelimiter("\\A").next().replaceAll("\"", "\'");
				content.close();
				scanner.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	protected void onPostExecute(String json) {
		super.onPostExecute(json);
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				progressDialog.dismiss();
			}
		});
		this.listener.onTaskCompleted(json);
	}
	
	public interface OnTaskCompleted {
		public void onTaskCompleted(String json);
	}
}
