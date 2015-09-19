package ranzo.hzregister.json;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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

import ranzo.hzregister.R;


public class JsonDownloader extends AsyncTask<String, String, String> {

	private final String URL = "https://enterprise.hanzo.com.br/contents/8/fields.json";
	
	private OnTaskCompleted listener;
	private Context context;
	private ProgressDialog progressDialog;
	
	public JsonDownloader(Context context, OnTaskCompleted listener ){
		this.listener = listener;
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(false);
		String msg = context.getResources().getString(R.string.dialog_message);
		progressDialog.setMessage(msg);
		progressDialog.show();
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
		progressDialog.dismiss();
		this.listener.onTaskCompleted(json);
	}
	
	public interface OnTaskCompleted {
		public void onTaskCompleted(String json);
	}
}
