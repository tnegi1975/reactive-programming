package com.rxjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.Subscriber;

public class RxJavaExamples {

	static ExecutorService executor = Executors.newFixedThreadPool(5);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("------------- start-------------");

		getDashboardData();

		System.out.println("------------- end-------------");

	}

	private static void getDashboardData() {
		System.out.println("Starting the main thread................"
				+ Thread.currentThread().getName());

		Observable<String> anomalydata = getAnamolyData("DataSource");
		Observable<String> alarmsdata = getAlarmsData("DataSource");
		Observable<String> healthdata = getHealthData("DataSource");

		Observable<String> zippedObservable = Observable.zip(anomalydata,
				alarmsdata, healthdata, (String anomaly, String alarms,
						String health) -> anomaly + "----" + alarms + " ---- "
						+ health);
		Observable<String> mergedObservable = Observable.merge(anomalydata,
				alarmsdata, healthdata);

		//subscribetoZippedStream(zippedObservable);
		subscribetoMergedStream(mergedObservable);

		
		System.out.println("Ending the main thread................"
				+ Thread.currentThread().getName());
	}
	
	

	private static void subscribetoMergedStream(
			Observable<String> mergedObservable) {
		mergedObservable.subscribe(new Subscriber<String>() {

			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNext(String t) {
				System.out
						.println("Total data collected for dashboard --- ----"
								+ t);
				System.out
				.println(" Notifying the UI Websocket for data updates..............." + t 
						+ "  --- " + Thread.currentThread().getName());

			}
		});
		
	}

	private static void subscribetoZippedStream(
			Observable<String> zippedObservable) {
		zippedObservable.subscribe(new Subscriber<String>() {
			String totaldata = null;

			@Override
			public void onCompleted() {

				System.out
						.println(" Notifying the UI Websocket for data updates..............."
								+ "  --- " +  Thread.currentThread().getName());
			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNext(String t) {
				totaldata = t;
				System.out
						.println("Total data collected for dashboard --- ----"
								+ t);

			}
		});

	}

	private static Observable<String> getAnamolyData(String book) {
		return Observable
				.create((Observable.OnSubscribe<String>) subscriber -> {
					Runnable r = () -> {
						subscriber.onNext(anomalysNetworkCall());
						subscriber.onCompleted();
					};
					executor.execute(r);
				});
	}

	private static Observable<String> getAlarmsData(String string) {
		return Observable
				.create((Observable.OnSubscribe<String>) subscriber -> {
					Runnable r = () -> {
						subscriber.onNext(alarmsNetworkCall());
						subscriber.onCompleted();
					};
					executor.execute(r);
				});
	}

	private static Observable<String> getHealthData(String string) {
		return Observable
				.create((Observable.OnSubscribe<String>) subscriber -> {
					Runnable r = () -> {
						subscriber.onNext(healthNetworkCall());
						subscriber.onCompleted();
					};
					executor.execute(r);
				});
	}

	private static String anomalysNetworkCall() {

		System.out
				.println("Starting Anomaly data network call to opentsdb......"
						+ "  --- " + Thread.currentThread().getName());

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out
				.println("Completed Anomaly data network call to opentsdb......"
						+ "  --- " + Thread.currentThread().getName());

		return "ANOMALY_DATA";
	}

	private static String alarmsNetworkCall() {

		System.out.println("Starting Alarms data network call to MongoDB......"
				+ "  --- " + Thread.currentThread().getName());

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out
				.println("Completed Alarms data network call to MongoDB......"
						+ "  --- " + Thread.currentThread().getName());

		return "ALARMS_DATA";
	}

	private static String healthNetworkCall() {

		System.out.println("Starting Health data network call to MongoDB......"
				+ "  --- " + Thread.currentThread().getName());

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out
				.println("Completed Health data network call to MongoDB......"
						+ "  --- " + Thread.currentThread().getName());

		return "HEALTH_DATA";
	}

}
