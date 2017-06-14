package game;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bang.Hand;
import bang.cards.Card;
import bang.gamestate.GameState;
import bang.gamestate.GameStateCard;
import bang.gamestate.GameStatePlayer;
import bang.userinterface.JSPUserInterface;
import bang.userinterface.Message;
import bang.userinterface.WebGameUserInterface;

@SuppressWarnings("serial")
public class AjaxServlet extends HttpServlet {
	
  public void doGet(HttpServletRequest request, HttpServletResponse response)
                               throws ServletException, IOException {
	  response.setContentType("text/xml"); 
      response.setHeader("Cache-Control", "no-cache");
      
      String messageType = request.getParameter("messageType");
      if(messageType != null && !messageType.equals("")){
    	if(messageType.equals("GETGAMESTATE")){
    		String gameId = request.getParameter("gameId");
	    	JSPUserInterface userInterface = (JSPUserInterface)WebInit.getUserInterface(Integer.parseInt(gameId));
	    	if(userInterface != null){
	    		GameState gameState = userInterface.getGameState();
	    		if(gameState != null){
		    		response.getWriter().write("<gamestate>");
			    		response.getWriter().write("<players>");
			    		for(GameStatePlayer player : gameState.getPlayers()){
			    			if (userInterface instanceof WebGameUserInterface){
			    				player.user = ((WebGameUserInterface)userInterface).userFigureNames.get(player.name);
			    			}
			    			writePlayer(player, response);
			    		}
			    		response.getWriter().write("</players>");
			    		if(gameState.timeout() != null){
			    			response.getWriter().write("<timeout>" + gameState.timeout() + "</timeout>");
			    		}
			    		if(gameState.isGameOver()){
			    			response.getWriter().write("<gameover/>");
			    			Cleanup cleanup = new Cleanup(Integer.parseInt(gameId));
			    			cleanup.start();
			    			WebGame.removeGame(Integer.parseInt(gameId));
			    		}
			    		response.getWriter().write("<currentname>");
			    		response.getWriter().write(gameState.getCurrentName());
			    		response.getWriter().write("</currentname>");
			    		response.getWriter().write("<decksize>");
			    		response.getWriter().write(Integer.toString(gameState.getDeckSize()));
			    		response.getWriter().write("</decksize>");
			    		GameStateCard topCard = gameState.discardTopCard();
			    		if(topCard != null){
			    			response.getWriter().write("<discardtopcard>");
			    			writeCard(topCard, response);
			    			response.getWriter().write("</discardtopcard>");
			    		}
		    		response.getWriter().write("</gamestate>");
	    		} else {
	    			response.getWriter().write("<gamestate/>");
	    		}
	    	} else {
	    		response.getWriter().write("<gamestate/>");
	    	}
    	} else if(messageType.equals("JOIN")){
    		String gameId = request.getParameter("gameId");
    		String handle = request.getParameter("handle");
    		String user = WebGame.join(Integer.parseInt(gameId), handle);
    		if(user != null){
    			response.getWriter().write("<joininfo>");
    			response.getWriter().write("<user>");
    			response.getWriter().write(user);
    			response.getWriter().write("</user>");
    			response.getWriter().write("<gameid>");
    			response.getWriter().write(gameId);
    			response.getWriter().write("</gameid>");
    			response.getWriter().write("</joininfo>");
    		} else {
    			response.getWriter().write("<fail/>");
    		}
    	} else if(messageType.equals("JOINAI")){
    		String gameId = request.getParameter("gameId");
    		String handle = request.getParameter("handle");
    		String user = WebGame.joinAI(Integer.parseInt(gameId), handle);
    		if(user != null){
    			response.getWriter().write("<joininfo>");
    			response.getWriter().write("<user>");
    			response.getWriter().write(user);
    			response.getWriter().write("</user>");
    			response.getWriter().write("<gameid>");
    			response.getWriter().write(gameId);
    			response.getWriter().write("</gameid>");
    			response.getWriter().write("</joininfo>");
    		} else {
    			response.getWriter().write("<fail/>");
    		}
    	} else if(messageType.equals("LEAVE")){
    		String user = request.getParameter("user");
    		String gameId = request.getParameter("gameId");
    		WebGame.leave(Integer.parseInt(gameId), user);
    		response.getWriter().write("<ok/>");
    	} else if(messageType.equals("COUNTPLAYERS")){
    		String gameId = request.getParameter("gameId");
    		response.getWriter().write("<count>");
    		response.getWriter().write("<playercount>");
    		if(gameId != null && !gameId.equals("null")){
    			response.getWriter().write(Integer.toString(WebGame.getCountPlayers(Integer.parseInt(gameId))));
    		} else {
    			response.getWriter().write("0");
    		}
    		response.getWriter().write("</playercount>");
    		response.getWriter().write("<players>");
    		List<String> joinedPlayers = WebGame.getJoinedPlayers(Integer.parseInt(gameId));
    		for(int playerIndex = 0; playerIndex < joinedPlayers.size(); playerIndex++){
    			String playerHandle = joinedPlayers.get(playerIndex);
    			response.getWriter().write("<playerName>");
	    		response.getWriter().write(playerHandle);
	    		response.getWriter().write("</playerName>");
    		}
    		response.getWriter().write("</players>");
    		response.getWriter().write("</count>");
    	} else if(messageType.equals("GETGUESTCOUNTER")){
    		response.getWriter().write("<guestcounter>");
    		response.getWriter().write(Integer.toString(WebGame.getNextGuestCounter()));
    		response.getWriter().write("</guestcounter>");
    	} else if(messageType.equals("AVAILABLEGAMES")){
    		response.getWriter().write("<gameids>");
    		List<Integer> availableGames = WebGame.getAvailableGames();
    		for(int i = 0; i < availableGames.size(); i++){
    			response.getWriter().write("<game>");
	    		response.getWriter().write("<gameid>");
	    		response.getWriter().write(Integer.toString(availableGames.get(i)));
	    		response.getWriter().write("</gameid>");
	    		response.getWriter().write("<playercount>");
	    		response.getWriter().write(Integer.toString(WebGame.getCountPlayers(availableGames.get(i))));
	    		response.getWriter().write("</playercount>");
	    		response.getWriter().write("<canjoin>");
	    		response.getWriter().write(Boolean.toString(WebGame.canJoin(availableGames.get(i))));
	    		response.getWriter().write("</canjoin>");
	    		response.getWriter().write("<players>");
	    		List<String> joinedPlayers = WebGame.getJoinedPlayers(availableGames.get(i));
	    		for(int playerIndex = 0; playerIndex < joinedPlayers.size(); playerIndex++){
	    			String playerHandle = joinedPlayers.get(playerIndex);
	    			response.getWriter().write("<playerName>");
		    		response.getWriter().write(playerHandle);
		    		response.getWriter().write("</playerName>");
	    		}
	    		response.getWriter().write("</players>");
	    		response.getWriter().write("</game>");
    		}
    		response.getWriter().write("</gameids>");
    	} else if(messageType.equals("CANSTART")){
    		String gameId = request.getParameter("gameId");
    		if(WebGame.canStart(Integer.parseInt(gameId))){
    			response.getWriter().write("<yes/>");
    		} else {
    			response.getWriter().write("<no/>");
    		}
    	} else if(messageType.equals("CHAT")){
    		String chat = request.getParameter("chat");
    		chat = chat.replace(">", "");
    		chat = chat.replace("<", "");
    		System.out.println("chat:" + chat);
    		String gameId = request.getParameter("gameid");
    		if(gameId == null){
    			gameId = "lobby";
    		}
    		WebGame.addChat(chat, gameId);
    		response.getWriter().write("<ok/>");
    	} else if(messageType.equals("GETCHAT")){
    		String guestCounter = request.getParameter("guestCounter");
    		String handle = request.getParameter("handle");
    		String gameId = request.getParameter("gameid");
    		if(gameId == null){
    			gameId = "lobby";
    		}
    		WebGame.updateSession(guestCounter, handle);
    		List<ChatMessage> chatLog = WebGame.getChatLog(gameId);
    		response.getWriter().write("<chats>");
    		for(ChatMessage chat : chatLog){
    			response.getWriter().write("<chatmessage>");
    			response.getWriter().write("<chat>");
    			response.getWriter().write(chat.message);
    			response.getWriter().write("</chat>");
    			response.getWriter().write("<timestamp>");
    			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    			response.getWriter().write(sdf.format(chat.timestamp));
    			response.getWriter().write("</timestamp>");
    			response.getWriter().write("</chatmessage>");
    		}
    		List<Session> sessions = WebGame.getSessions();
    		for(Session session : sessions){
    			response.getWriter().write("<session>");
    			String outHandle = session.handle;
    			if(outHandle == null){
    				outHandle = "Unknown";
    			}
    			response.getWriter().write(outHandle);
    			response.getWriter().write("</session>");
    		}
    		response.getWriter().write("</chats>");
    	} else if(messageType.equals("START")){
    		String gameId = request.getParameter("gameId");
    		String aiSleepMs = request.getParameter("aiSleepMs");
    		WebGame.start(Integer.parseInt(gameId), Integer.parseInt(aiSleepMs));
    		response.getWriter().write("<ok/>");
    	} else if(messageType.equals("CREATE")){    		
    		int gameId = WebGame.create();    		
    		response.getWriter().write("<gameid>");
    		response.getWriter().write(Integer.toString(gameId));
    		response.getWriter().write("</gameid>");
    	} else if(messageType.equals("GETMESSAGE")){
    		String user = request.getParameter("user");
    		String gameId = request.getParameter("gameId");
    		JSPUserInterface userInterface = (JSPUserInterface)WebInit.getUserInterface(Integer.parseInt(gameId));
    		if(userInterface != null){
	    		List<Message> messages = ((WebGameUserInterface)userInterface).getMessages(user);
	    		if(!messages.isEmpty()){
	    			System.out.println("Got message " + messages.get(0));
	    			response.getWriter().write("<message>");
	    			response.getWriter().write("<id>");
	    			response.getWriter().write(Integer.toString(messages.get(0).getId()));
	    			response.getWriter().write("</id>");
	    			response.getWriter().write("<text>");
	    			response.getWriter().write(messages.get(0).getMessage());
	    			response.getWriter().write("</text>");
	    			response.getWriter().write("<hand>");
	    			if(userInterface.isPlayerAlive(((WebGameUserInterface)userInterface).getPlayerForUser(user))){
		    			Hand hand = userInterface.getHandForUser(((WebGameUserInterface)userInterface).getPlayerForUser(user));
		    			for(int i = 0; i < hand.size(); i++){
		    				Card card = (Card)hand.get(i);
		    				response.getWriter().write("<card>");
		    				response.getWriter().write(card.getName());
		    				response.getWriter().write("</card>");
		    			}
		    		}
	    			response.getWriter().write("</hand>");
	    			response.getWriter().write("</message>");
	    		} else {
	    			response.getWriter().write("<ok/>");
	    		}
    		} else {
    			response.getWriter().write("<ok/>");
    		}
    	} else if(messageType.equals("SENDRESPONSE")){
    		System.out.println("Sent Response");
    		String user = request.getParameter("user");
    		String responseMessage = request.getParameter("response");
    		String gameId = request.getParameter("gameId");
    		String messageId = request.getParameter("messageId");
    		System.out.println("Response " + messageId);
    		JSPUserInterface userInterface = (JSPUserInterface)WebInit.getUserInterface(Integer.parseInt(gameId));
    		if(userInterface != null){
	    		List<Message> messages = ((WebGameUserInterface)userInterface).getMessages(user);
	    		if(!messages.isEmpty()){
		    		Object removed = messages.remove(0);
		    		System.out.println("Removed " + removed);
		    		if(!"".equals(responseMessage)){
		    			((WebGameUserInterface)userInterface).addResponse(user, responseMessage);
		    		}
	    		} else {
	    			System.out.println("**Weirdo Ajax Servlet sent response to empty messages " + user + " " + responseMessage + "**");
	    		}
    		}
    		response.getWriter().write("<ok/>");
    	} else if(messageType.equals("GETPLAYERINFO")){
    		String user = request.getParameter("user");
    		String gameId = request.getParameter("gameId");
    		JSPUserInterface userInterface = (JSPUserInterface)WebInit.getUserInterface(Integer.parseInt(gameId));
    		if(userInterface != null){
	    		String name = ((WebGameUserInterface)userInterface).getPlayerForUser(user);
	    		String role = userInterface.getRoleForName(name);
	    		String goal = userInterface.getGoalForName(name);
	    		response.getWriter().write("<userinfo>");
	    			response.getWriter().write("<name>");
	    			response.getWriter().write(name);
	    			response.getWriter().write("</name>");
	    			response.getWriter().write("<role>");
	    			response.getWriter().write(role);
	    			response.getWriter().write("</role>");
	    			response.getWriter().write("<goal>");
	    			response.getWriter().write(goal);
	    			response.getWriter().write("</goal>");
	    		response.getWriter().write("</userinfo>");
    		} else {
    			response.getWriter().write("<ok/>");
    		}
    	}
      }
  }
  
  private void writePlayer(GameStatePlayer player, HttpServletResponse response) throws IOException{
	  	response.getWriter().write("<player>");
	  	response.getWriter().write("<handle>");
		response.getWriter().write(player.user);
		response.getWriter().write("</handle>");
	  	response.getWriter().write("<name>");
		response.getWriter().write(player.name);
		response.getWriter().write("</name>");
		response.getWriter().write("<specialability>");
		response.getWriter().write(player.specialAbility);
		response.getWriter().write("</specialability>");
		response.getWriter().write("<health>");
		response.getWriter().write(Integer.toString(player.health));
		response.getWriter().write("</health>");
		response.getWriter().write("<maxhealth>");
		response.getWriter().write(Integer.toString(player.maxHealth));
		response.getWriter().write("</maxhealth>");
		response.getWriter().write("<handsize>");
		response.getWriter().write(Integer.toString(player.handSize));
		response.getWriter().write("</handsize>");
		if(player.isSheriff){
			response.getWriter().write("<issheriff/>");
		}
		if(player.gun != null){
			response.getWriter().write("<gun>");
			writeCard(player.gun, response);
			response.getWriter().write("</gun>");
		}
		List<GameStateCard> inPlay = player.inPlay;
		if(inPlay != null && !inPlay.isEmpty()){
			response.getWriter().write("<inplay>");
			for(GameStateCard inPlayCard : inPlay){
				response.getWriter().write("<inplaycard>");
				writeCard(inPlayCard, response);
				response.getWriter().write("</inplaycard>");
			}			
			response.getWriter().write("</inplay>");
		}
		response.getWriter().write("</player>");
  }
  
  private void writeCard(GameStateCard card, HttpServletResponse response) throws IOException{	  	
		response.getWriter().write("<name>");
		response.getWriter().write(card.name);
		response.getWriter().write("</name>");
		response.getWriter().write("<suit>");
		response.getWriter().write(card.suit);
		response.getWriter().write("</suit>");
		response.getWriter().write("<value>");
		response.getWriter().write(card.value);
		response.getWriter().write("</value>");
		response.getWriter().write("<type>");
		response.getWriter().write(card.type);
		response.getWriter().write("</type>");
  }
  
  class Cleanup extends Thread{		
		int gameId;
		Cleanup(int gameId){
				this.gameId = gameId;
		}
		public void run(){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				//ignore
			}
			WebInit.remove(gameId);
		}
	}
}