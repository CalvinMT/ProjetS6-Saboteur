package Cards;


public class PlayerCard extends Card {

        String role;

        PlayerCard(String r){
            if(r == "Mineur" || r == "Saboteur"){
                this.role = r;
            } else {
                this.role = "Mineur";
            }
        }

        PlayerCard(){
            this.role = "Mineur";
        }

        public boolean isMinor(){
            return (this.role == "Mineur");
        }

        public boolean isSaboteur(){
            return (this.role == "Saboteur");
        }

        public String toString(){
            return "Role: "+this.role;
        }
}
