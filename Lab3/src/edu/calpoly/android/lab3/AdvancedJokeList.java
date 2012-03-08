package edu.calpoly.android.lab3;

import java.net.MalformedURLException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AdvancedJokeList extends Activity {
	/**
	 * Contains the name of the Author for the jokes.
	 */
	protected String m_strAuthorName;

	/**
	 * Contains the list of Jokes the Activity will present to the user.
	 **/
	protected ArrayList<Joke> m_arrJokeList;
	protected ArrayList<Joke> m_arrJokeCopy;

	/**
	 * Adapter used to bind an AdapterView to List of Jokes.
	 */
	protected JokeListAdapter m_jokeAdapter;

	/**
	 * ViewGroup used for maintaining a list of Views that each display Jokes.
	 **/
	protected ListView m_vwJokeLayout;

	/**
	 * EditText used for entering text for a new Joke to be added to
	 * m_arrJokeList.
	 **/
	protected EditText m_vwJokeEditText;

	/**
	 * Button used for creating and adding a new Joke to m_arrJokeList using the
	 * text entered in m_vwJokeEditText.
	 **/
	protected Button m_vwJokeButton;

	/**
	 * Background Color values used for alternating between light and dark rows
	 * of Jokes.
	 */
	protected int m_nDarkColor;
	protected int m_nLightColor;

	protected MenuItem menuItem;
	protected MenuItem rateItem;

	/**
	 * Context-Menu MenuItem ID's IMPORTANT: You must use these when creating
	 * your MenuItems or the tests used to grade your submission will fail.
	 */
	protected static final int REMOVE_JOKE_MENUITEM = Menu.FIRST;
	protected static final int UPLOAD_JOKE_MENUITEM = Menu.FIRST + 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initLayout();
		initAddJokeListeners();

		Resources res = getResources();

		m_nDarkColor = res.getColor(R.color.dark);
		m_nLightColor = res.getColor(R.color.light);
		m_strAuthorName = res.getString(R.string.author_name);

		m_arrJokeList = new ArrayList<Joke>();
		m_arrJokeCopy = new ArrayList<Joke>();
		m_jokeAdapter = new JokeListAdapter(this, m_arrJokeCopy);
		m_vwJokeLayout.setAdapter(m_jokeAdapter);
		m_vwJokeLayout
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						m_jokeAdapter.onItemLongClick(arg0, arg1, arg2, arg3);
						return false;
					}
				});

		String[] jokes = getResources().getStringArray(R.array.jokeList);
		for (int j = 0; j < jokes.length; j++) {
			addJoke(new Joke(jokes[j], m_strAuthorName));
		}

		registerForContextMenu(m_vwJokeLayout);
	}

	/**
	 * Method is used to encapsulate the code that initializes and sets the
	 * Layout for this Activity.
	 */
	protected void initLayout() {
		setContentView(R.layout.advanced);
		m_vwJokeLayout = (ListView) findViewById(R.id.jokeListViewGroup);
		m_vwJokeLayout.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		m_vwJokeEditText = (EditText) findViewById(R.id.newJokeEditText);
		m_vwJokeButton = (Button) findViewById(R.id.addJokeButton);
	}

	/**
	 * Method is used to encapsulate the code that initializes and sets the
	 * Event Listeners which will respond to requests to "Add" a new Joke to the
	 * list.
	 */
	protected void initAddJokeListeners() {
		// TODO
		m_vwJokeButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (m_vwJokeButton == (Button) v
						&& !m_vwJokeEditText.getText().toString().equals("")) {
					addJoke(new Joke(m_vwJokeEditText.getText().toString(),
							m_strAuthorName));
					m_vwJokeEditText.setText("");

					// Hide Keyboard
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							m_vwJokeEditText.getWindowToken(), 0);
				}
			}
		});

		m_vwJokeEditText.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (m_vwJokeEditText == (EditText) v
						&& (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER)
						&& event.getAction() == KeyEvent.ACTION_DOWN
						&& !m_vwJokeEditText.getText().toString().equals("")) {

					addJoke(new Joke(m_vwJokeEditText.getText().toString(),
							m_strAuthorName));
					m_vwJokeEditText.setText("");

					// Hide Keyboard
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							m_vwJokeEditText.getWindowToken(), 0);

					return true;
				}
				return false;
			}
		});
	}

	/**
	 * Method used for encapsulating the logic necessary to properly add a new
	 * Joke to m_arrJokeList, and display it on screen.
	 * 
	 * @param joke
	 *            The Joke to add to list of Jokes.
	 */
	protected void addJoke(Joke joke) {
		if (joke != null) {
			m_arrJokeList.add(joke);
			m_arrJokeCopy.add(joke);
			m_jokeAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menuItem = menu.add(0, REMOVE_JOKE_MENUITEM, 0,
				R.string.remove_menuitem);
		menuItem = menu.add(0, UPLOAD_JOKE_MENUITEM, 0,
				R.string.upload_menuitem);

		menuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			public boolean onMenuItemClick(MenuItem item) {
				// Toast toast = Toast.makeText(getApplicationContext(),
				// "Lol",Toast.LENGTH_SHORT);
				// toast.show();

				// if (item.getItemId() == REMOVE_JOKE_MENUITEM) {

				// m_arrJokeCopy.remove(m_jokeAdapter.getSelectedPosition());
				// m_jokeAdapter.notifyDataSetChanged();

				// return true;
				// }
				// if (item.getItemId() == UPLOAD_JOKE_MENUITEM) {

				// }
				uploadJokeToServer(m_arrJokeCopy.get(m_jokeAdapter
						.getSelectedPosition()));
				return false;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.like_menuitem:
			m_arrJokeCopy.clear();
			for (int i = 0; i < m_arrJokeList.size(); i++) {
				if (m_arrJokeList.get(i).getRating() == Joke.LIKE) {
					m_arrJokeCopy.add(m_arrJokeList.get(i));
				}
			}
			m_jokeAdapter.notifyDataSetChanged();
			return true;
		case R.id.dislike_menuitem:
			m_arrJokeCopy.clear();
			for (int i = 0; i < m_arrJokeList.size(); i++) {
				if (m_arrJokeList.get(i).getRating() == Joke.DISLIKE) {
					m_arrJokeCopy.add(m_arrJokeList.get(i));
				}
			}
			m_jokeAdapter.notifyDataSetChanged();
			return true;
		case R.id.unrated_menuitem:
			m_arrJokeCopy.clear();
			for (int i = 0; i < m_arrJokeList.size(); i++) {
				if (m_arrJokeList.get(i).getRating() == Joke.UNRATED) {
					m_arrJokeCopy.add(m_arrJokeList.get(i));
				}
			}
			m_jokeAdapter.notifyDataSetChanged();
			return true;
		case R.id.show_all_menuitem:
			m_arrJokeCopy.clear();
			for (int i = 0; i < m_arrJokeList.size(); i++) {
				m_arrJokeCopy.add(m_arrJokeList.get(i));
			}
			m_jokeAdapter.notifyDataSetChanged();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Method used to retrieve Jokes from online server. The getJoke script
	 * takes a single optional parameter, which should be encode in "UTF-8".
	 * This parameter allows tells script to only retrieve Jokes whose author
	 * name matches the value in the parameter.
	 * 
	 * param-1) "author": The author of the joke.
	 * 
	 * URL: http://simexusa.com/aac/getJokes.php?
	 * 
	 * @throws MalformedURLException
	 * 
	 */
	protected void getJokesFromServer() throws MalformedURLException {
		// TODO

		java.net.URL URL = new java.net.URL(
				"http://simexusa.com/aac/getAllJokes.php");
		

	}

	/**
	 * This method uploads a single Joke to the server. This method should test
	 * the response from the server and display success or failure to the user
	 * via a Toast Notification
	 * 
	 * The addJoke script on the server requires two parameters, both of which
	 * should be encode in "UTF-8":
	 * 
	 * param-1) "joke": The text of the joke.
	 * 
	 * param-2) "author": The author of the joke.
	 * 
	 * URL: http://simexusa.com/aac/addJoke.php?
	 * 
	 * @param joke
	 *            The Joke to be uploaded to the server.
	 * @throws MalformedURLException
	 * 
	 */
	protected void uploadJokeToServer(Joke joke) throws MalformedURLException {

		String mURL = "http://simexusa.com/aac/addOneJoke.php?";
		mURL += "joke=";
		mURL += java.net.URLEncoder.encode(joke.getJoke());
		mURL += "&author";
		mURL += java.net.URLEncoder.encode(joke.getAuthor());

		java.net.URL URL = new java.net.URL(mURL);
		URL.openStream();
		Toast toast = Toast.makeText(getApplicationContext(), "Uploaded",
				Toast.LENGTH_SHORT);
		toast.show();
	}

}