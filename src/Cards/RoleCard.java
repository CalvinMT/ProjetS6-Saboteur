package Cards;


public class RoleCard extends Card {

        String role;

        RoleCard(String r){
            this.type = Card_t.role;
            if(r == "Mineur" || r == "Saboteur"){
                this.role = r;
            } else {
                this.role = "Mineur";
            }
        }

        RoleCard(){
            this.role = "Mineur";
            this.type = Card_t.role;
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
