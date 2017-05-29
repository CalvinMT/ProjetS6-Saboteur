package Cards;


import java.util.Objects;

public class RoleCard extends Card {

        String role;

        public RoleCard(String r){
            this.type = Card_t.role;
            if(r == "Mineur" || r == "Saboteur"){
                this.role = r;
            } else {
                this.role = "Mineur";
            }
        }

        public RoleCard(){
            this.role = "Mineur";
            this.type = Card_t.role;
        }

        public boolean isMiner(){
            return (Objects.equals(this.role, "Mineur"));
        }

        public boolean isSaboteur(){
            return (Objects.equals(this.role, "Saboteur"));
        }

        public String toString(){
            //return "Role: "+this.role;
        	return this.role;
        }
        
        @Override
    	public int getGold(){
    		return 0;
    	}
}
