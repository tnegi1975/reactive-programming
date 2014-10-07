package com.rxjava;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestClass {

	public static void main(String[] args) {
		System.out.println("-----start ---------");
		String name = "ravi";

		Runnable noArguments = () -> {
			System.out.println("Hello World in run");
		};
		// noArguments.run();
		//
		// ActionListener oneArgument = event ->
		// System.out.println("button clicked" + event);
		//
		// ActionEvent e = new ActionEvent("event", 0, "my event");
		// oneArgument.actionPerformed(e);
		//
		// oneArgument.actionPerformed(e);
		//
		//
		// BinaryOperator<Long> add = (x, y) -> x + y;
		//
		// Long ans = add.apply(new Long(5), new Long(5));
		//
		// System.out.println("answer---" + ans );

		// runnableExecutor(() ->
		// {System.out.println("Hello World in run 123");});
		ArrayList<String> allArtists = new ArrayList();
		allArtists.add("ram");
		allArtists.add("mohan");
		allArtists.add("ravi");
		allArtists.add("rohan");
		allArtists.add("himanshu");
		allArtists.add("neha");

         long c = allArtists.stream().filter(artist -> {
			System.out.println(artist);
			if(artist.equals("rohan"))
				artist = "ravi";
			return artist.equals("ravi");
		}).count();
         
         System.out.println("count === " + c);
         
         List<String> collected = Stream.of("a", "b", "c").filter(val -> {return val.equals("b");})
        		 .collect(Collectors.toList());
         
         System.out.println("collected === " + collected);
         
		
		System.out.println("-----end ---------");

	}

	public static void runnableExecutor(Runnable r) {

		System.out.println("Hello World 2");
		r.run();
	};

}
