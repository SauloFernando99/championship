package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;
import java.util.*;

public class RoundRobin extends Championship{
    private List<Round> table;
    private Integer TeamAmount;
    private List<Team> teams;

    public RoundRobin(Integer idChampionship, LocalDate initialDate, LocalDate finalDate, String modality, String award, String sponsorship, List<Team> teams, List<Round> table, Integer teamAmount) {
        super(idChampionship, initialDate, finalDate, modality, award, sponsorship, teams);
        this.table = table;
        if(teamAmount %2 == 0) {
            this.setTeamAmount(teamAmount);
        }
        else{
            throw new IllegalArgumentException("The number of teams must be an even number!");
        }
    }

    public static boolean verifyAmountTeams(List<Team> listTeams, Integer teamAmount) {
        if (listTeams.size() == teamAmount) {
            System.out.println("The number of teams on the list is equal to " + teamAmount);
            return true;
        } else {
            System.out.println("The number of teams on the list isnt equal to " + teamAmount);
            return false;
        }
    }

    private boolean teamsAlreadyPlayed(Team team1, Team team2) {
        for (Round round : table) {
            for (Match match : round.getMatches()) {
                if ((match.getTeam1().equals(team1) && match.getTeam2().equals(team2)) ||
                        (match.getTeam1().equals(team2) && match.getTeam2().equals(team1))) {
                    return true; // Os times já se enfrentaram
                }
            }
        }
        return false; // Os times ainda não se enfrentaram
    }

    public void createRound(RoundRobin roundRobin) {
        if (verifyAmountTeams(this.getTeams(), this.getTeamAmount())) {
            // Verificar se a última rodada foi finalizada
            if (!table.isEmpty()) {
                Round lastRound = table.get(table.size() - 1);
                if (!allMatchConcluedOnRound(lastRound)) {
                    System.out.println("A última rodada ainda não foi finalizada. Não é possível criar uma nova rodada.");
                    return;
                }
            }
            // Criar uma rodada
            Round round = new Round(table.size() + 1, LocalDate.now(), new ArrayList<>());

            // Copiar a lista de times para não modificar a original
            List<Team> copyTeams = new ArrayList<>(teams);

            // Adicionar cada partida à rodada
            for (int i = 0; i < (TeamAmount / 2); i++) {
                Collections.shuffle(copyTeams); // Embaralhar a lista para garantir partidas aleatórias

                Team team1 = copyTeams.get(0);
                Team team2 = copyTeams.get(1);

                // Verificar se os times são diferentes e não se enfrentaram anteriormente
                while (team1.equals(team2) || teamsAlreadyPlayed(team1, team2)) {
                    Collections.shuffle(copyTeams);
                    team1 = copyTeams.get(0);
                    team2 = copyTeams.get(1);
                }

                // Criar a partida e adicioná-la à rodada
                Match match = new Match(team1, team2);
                round.getMatches().add(match);

                // Remover os times utilizados da cópia
                copyTeams.remove(team1);
                copyTeams.remove(team2);
            }

            // Adicionar a rodada à tabela
            table.add(round);
        }
    }

    public void showTeamsActualRound() {
        if (table.isEmpty()) {
            System.out.println("Rounds not yet generated");
            return;
        }

        Round lastRound = table.get(table.size() - 1);
        List<Match> matches = lastRound.getMatches();

        System.out.println("Times na Rodada Atual:");

        for (Match match : matches) {
            Team team1 = match.getTeam1();
            Team team2 = match.getTeam2();

            String matchResult = (match.getScoreboard1() != null && match.getScoreboard2() != null)
                    ? " - Resultado: " + match.getScoreboard1() + " x " + match.getScoreboard2()
                    : "";

            System.out.println("Partida: " + team1.getName() + " vs " + team2.getName() + matchResult);
        }
    }

    public void manageLastRoundMatch() {
        if (table.isEmpty()) {
            System.out.println("Ainda não foram geradas rodadas.");
            return;
        }
        Round lastRound = table.get(table.size() - 1);
        List<Match> matches = lastRound.getMatches();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha uma partida para gerenciar:");

        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i).getTeam1().getName() + " vs " + matches.get(i).getTeam2().getName());
        }

        int escolha = scanner.nextInt();

        if (escolha < 1 || escolha > matches.size()) {
            System.out.println("Escolha inválida.");
            return;
        }

        Match selectedMatch = matches.get(escolha - 1);
        if (selectedMatch.getConcluded()) {
            System.out.println("A partida já foi finalizada. Não é possível atualizar o resultado.");
            return;
        }

        System.out.println("Informe o resultado da partida " + selectedMatch.getTeam1().getName() + " vs " + selectedMatch.getTeam2().getName() + ":");
        System.out.print("Gols do time " + selectedMatch.getTeam1().getName() + ": ");
        int golsTimeA = scanner.nextInt();

        System.out.print("Gols do time " + selectedMatch.getTeam2().getName() + ": ");
        int golsTimeB = scanner.nextInt();

        // Validar placares não negativos
        if (golsTimeA < 0 || golsTimeB < 0) {
            System.out.println("Os placares não podem ser negativos.");
            return;
        }

        // Registrar resultado usando o novo método
        selectedMatch.updateMatch(selectedMatch.getIdMatch(), golsTimeA, golsTimeB);

        System.out.println("\nA partida já foi finalizada? (Digite 'sim' ou 'nao')");
        String finalizada = scanner.next().toLowerCase();

        if ("sim".equals(finalizada)) {
            selectedMatch.setConcluded(true);
            selectedMatch.updatePunctuation(selectedMatch);
            System.out.println("Resultado da partida registrado com sucesso!");
        } else if ("nao".equals(finalizada)) {
            System.out.println("A pontuação só será registrada na tabela caso a partida tenha acabado!");
        }
    }

    public void printTable() {
        if (teams.isEmpty()) {
            System.out.println("Ainda não há times cadastrados.");
            return;
        }

        // Ordenar a lista de times pelo número de pontos (descendente)
        teams.sort(Comparator.comparing(Team::getPoints).reversed());

        System.out.println("Tabela de Pontos Corridos:");

        System.out.printf("%-20s %-8s %-8s %-8s %-12s %-8s%n",
                "Time", "Vitórias", "Empates", "Derrotas", "Saldo de Gols", "Pontos");

        for (Team team : teams) {
            System.out.printf("%-20s %-8d %-8d %-8d %-12d %-8d%n",
                    team.getName(), team.getWins(), team.getDraw(), team.getLoses(),
                    team.getGoalDifference(), team.getPoints());
        }
    }


    public boolean allMatchConcluedOnRound(Round round) {
        List<Match> matches = round.getMatches();

        for (Match match : matches) {
            if (!match.getConcluded()) {
                return false; // Se encontrar uma partida não finalizada, retorna false
            }
        }
        return true; // Se todas as partidas estiverem finalizadas, retorna true
    }

    public void addRound(Round round){table.add(round);}

    public void addTeam(Team team){teams.add(team);}

    public List<Round> getTable() {
        return table;
    }

    public void setTable(List<Round> table) {
        this.table = table;
    }

    public Integer getTeamAmount() {
        return TeamAmount;
    }

    public void setTeamAmount(Integer teamAmount) {
        TeamAmount = teamAmount;
    }
    public List<Team> getTeams() {
        return teams;
    }
    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
}
