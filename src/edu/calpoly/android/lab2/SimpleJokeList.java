package edu.calpoly.android.lab2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SimpleJokeList extends Activity {

	/** Contains the list Jokes the Activity will present to the user **/
	protected ArrayList<Joke> m_arrJokeList;

	/**
	 * LinearLayout used for maintaining a list of Views that each display Jokes
	 **/
	protected LinearLayout m_vwJokeLayout;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_arrJokeList = new ArrayList<Joke>();
		initLayout();
		getResources();
		String[] jokes = getResources().getStringArray(R.array.jokeList);
		for (int j = 0; j < jokes.length; j++) {
			addJoke(jokes[j]);
		}
		initAddJokeListeners();
	}

	/**
	 * Method used to encapsulate the code that initializes and sets the Layout
	 * for this Activity.
	 */
	protected void initLayout() {
		LinearLayout rootLayout = new LinearLayout(this);
		rootLayout.setOrientation(LinearLayout.VERTICAL);

		LinearLayout topLayout = new LinearLayout(this);
		topLayout.setOrientation(LinearLayout.HORIZONTAL);
		rootLayout.addView(topLayout);

		m_vwJokeButton = new Button(this);
		m_vwJokeButton.setText(R.string.add_joke);
		topLayout.addView(m_vwJokeButton);

		m_vwJokeEditText = new EditText(this);
		m_vwJokeEditText.setHint(R.string.enter_joke);
		m_vwJokeEditText.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		m_vwJokeEditText.setTextSize(16);
		m_vwJokeEditText.setInputType(InputType.TYPE_CLASS_TEXT);
		topLayout.addView(m_vwJokeEditText);

		ScrollView scrollView = new ScrollView(this);
		rootLayout.addView(scrollView);

		m_vwJokeLayout = new LinearLayout(this);
		m_vwJokeLayout.setOrientation(LinearLayout.VERTICAL);
		scrollView.addView(m_vwJokeLayout);

		setContentView(rootLayout);
	}

	/**
	 * Method used to encapsulate the code that initializes and sets the Event
	 * Listeners which will respond to requests to "Add" a new Joke to the list.
	 */
	protected void initAddJokeListeners() {
		m_vwJokeButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				String joke = m_vwJokeEditText.getText().toString();
				if (joke != "")
					addJoke(joke);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

				imm.hideSoftInputFromWindow(m_vwJokeEditText.getWindowToken(),
						0);
			}
		});

		m_vwJokeEditText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					String joke = m_vwJokeEditText.getText().toString();
					if (joke != "")
						addJoke(joke);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

					imm.hideSoftInputFromWindow(
							m_vwJokeEditText.getWindowToken(), 0);
				}
				return false;
			}
		});
	}

	/**
	 * Method used for encapsulating the logic necessary to properly initialize
	 * a new joke, add it to m_arrJokeList, and display it on screen.
	 * 
	 * @param strJoke
	 *            A string containing the text of the Joke to add.
	 */
	protected void addJoke(String strJoke) {
		m_arrJokeList.add(new Joke(strJoke));
		TextView textView = new TextView(this);

		textView.setBackgroundColor(m_arrJokeList.size() % 2 == 1 ? 0xFF1F1F1F
				: 0xFF3D3D3D);
		textView.setText(strJoke);
		textView.setTextSize(16);
		
		LinearLayout jokeLayout = new LinearLayout(this);
		jokeLayout.setOrientation(LinearLayout.HORIZONTAL);
		m_vwJokeLayout.addView(jokeLayout);
		
		Button jokeButton = new Button(this);
		jokeButton.setText("+");
		jokeLayout.addView(jokeButton);
		jokeLayout.addView(textView);
	}
}