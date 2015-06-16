package com.jk.demo2;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.jk.adapter.MyAdapter;
import com.jk.view.LoadMoreListView;
import com.jk.view.LoadMoreListView.OnLoadMoreListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

	private List<String> list;

	/** 自定义myListView ***/
	@ViewInject(R.id.myListView)
	LoadMoreListView myListView;

	private View headerView;

	private LinearLayout navContent;

	private LinearLayout navContainer1;

	@ViewInject(R.id.float_container2)
	private LinearLayout navContainer2;

	private MyAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initHeaderView();
		initNavContent();
		navContainer();

		initListData();
		initListView();

	}

	private void initListData() {
		list = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			list.add("测试");
		}
	}

	private void initListView() {
		myListView.addHeaderView(headerView);
		myListView.addHeaderView(navContainer1);

		myListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= 1) {
					navContainer1.removeView(navContent);
					if (navContent.getParent() == null) {
						navContainer2.addView(navContent,
								LayoutParams.MATCH_PARENT, 60);
					}

				} else {
					navContainer2.removeView(navContent);
					if (navContent.getParent() == null) {
						navContainer1.addView(navContent,
								LayoutParams.MATCH_PARENT, 60);
					}

				}
			}
		});

		myListView.setOnLoadMoreListener(new OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				new LoadMoreDataTask().execute();
			}
		});

		myAdapter = new MyAdapter(list, this);
		myListView.setAdapter(myAdapter);
	}

	private void initNavContent() {
		navContent = (LinearLayout) View.inflate(this, R.layout.item_nav, null);
	}

	private void navContainer() {
		navContainer1 = (LinearLayout) View.inflate(this, R.layout.nav_view,
				null);
	}

	private void initHeaderView() {
		headerView = View.inflate(this, R.layout.header_view, null);
	}

	private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			if (isCancelled()) {
				return null;
			}

			// Simulates a background task
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			for (int i = 0; i < 10; i++)
				list.add("测试");

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// mListItems.add("Added after load more");
			//
			// // We need notify the adapter that the data have been changed
			myAdapter.notifyDataSetChanged();
			//
			// // Call onLoadMoreComplete when the LoadMore task, has finished
			myListView.onLoadMoreComplete();

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// Notify the loading more operation has finished
			myListView.onLoadMoreComplete();
		}
	}
}
