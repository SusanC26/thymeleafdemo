package es.secaro.thymeleafdemo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import es.secaro.thymeleafdemo.dto.Raffle;

@Controller
public class RaffleController {
	
	@GetMapping("/raffle")
	public String raffleForm(Model model) {
		model.addAttribute("raffle", new Raffle());
		return "raffle";
	}
	
	@PostMapping("/raffle")
	public String raffleSubmit(@ModelAttribute Raffle raffle) {
		
		String candidates = raffle.getCandidates();		
		List<String> winners = doRaffle(asList(candidates));

		raffle.setWinners(winners);
		return "result";
	}

	private List<String> doRaffle(List<String> candidates) {
		
		Random random = new Random();
		List<String> winners = new ArrayList<String>();		
		
		int winnerSize = new HashSet<String>(candidates).size(); // HashSet elimina duplicados		
		while(winnerSize>0){
			String nextWinner = candidates.get(random.nextInt(candidates.size()));	
			while(winners.contains(nextWinner)){
				nextWinner = candidates.get(random.nextInt(candidates.size()));	
			}
			winners.add(nextWinner);
			winnerSize--;
		}
		return winners;
	}

	private List<String> asList(String candidates) {
		
		List<String> candidatesAsList = new ArrayList<String>();
			for (String line : candidates.split("\\n")) {
				
				String[] split = line.split(",");
				String name = split[0].trim();
				int tickets = Integer.valueOf(split[1].trim());

				while (tickets > 0) {
					candidatesAsList.add(name);
					tickets--;
				}
			}
			return candidatesAsList;		
	}

}
