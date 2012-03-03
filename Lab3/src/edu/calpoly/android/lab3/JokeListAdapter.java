package edu.calpoly.android.lab3;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

public class JokeListAdapter extends BaseAdapter  {

	/**
	 * The application Context in which this JokeListAdapter is being used.
	 */
	private Context m_context;

	/**
	 * The dataset to which this JokeListAdapter is bound.
	 */
	private List<Joke> m_jokeList;

	/**
	 * The position in the dataset of the currently selected Joke.
	 */
	private int m_nSelectedPosition;

	/**
	 * Parameterized constructor that takes in the application Context in which
	 * it is being used and the Collection of Joke objects to which it is bound.
	 * m_nSelectedPosition will be initialized to Adapter.NO_SELECTION.
	 * 
	 * @param context
	 *            The application Context in which this JokeListAdapter is being
	 *            used.
	 * 
	 * @param jokeList
	 *            The Collection of Joke objects to which this JokeListAdapter
	 *            is bound.
	 */
	public JokeListAdapter(Context context, List<Joke> jokeList) {
		m_context=context;
		m_jokeList=jokeList;
		m_nSelectedPosition=Adapter.NO_SELECTION;
	}

	/**
	 * Accessor method for retrieving the position in the dataset of the
	 * currently selected Joke.
	 * 
	 * @return an integer representing the position in the dataset of the
	 *         currently selected Joke.
	 */
	public int getSelectedPosition() {
		return m_nSelectedPosition;
	}

	public int getCount() {
		return m_jokeList.size();
	}

	public Object getItem(int position) {
		return m_jokeList.get(position);
	}

	public long getItemId(int position) {
		return m_jokeList.indexOf(m_jokeList.get(position));
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return new JokeView(m_context, m_jokeList.get(position));
	}
}
