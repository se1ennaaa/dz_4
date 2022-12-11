import java.util.Random;
public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;

    public static int[] heroesHealth = {270, 260, 250,800,500,400, 2000,200};
    public static int[] heroesDamage = {20, 15, 10,0,20,40,5,25};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic","Medic","Thor","Lucky","Golem","Berserk"};
    public static int roundNumber;
    public static String criticalMessage;


    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            playRound();
        }
    } public static void medicSkill(){
        for (int i = 0; i <heroesHealth.length ; i++) {
            if(i==3){
                continue;
            }
            if (heroesHealth[i]>0 && heroesHealth[i]<100 && heroesHealth[3]>0) {
                heroesHealth[i]+= 30;
                System.out.println("Медик вылечил "+ heroesAttackType[i]);
                break;
            }

        }
    }
    public static void thorSkill(){
        Random rn =new Random();
        boolean attack = rn.nextBoolean();
        for (int i = 0; i <heroesHealth.length ; i++) {
            if (heroesHealth[4]>0){
                if (attack){
                    bossDamage=0;
                    System.out.println("Тор оглушил Босса "+ attack);
                    break;
                }
            }else {
                bossDamage=50;
                break;
            }
        }
    }

    public static void luckySkill(){
        Random rn =new Random();

        boolean lucky = rn.nextBoolean();
        if (heroesHealth[5]>0 && lucky){
            heroesHealth[5]+=bossDamage;
            System.out.println("Lucky уклонился от босса " + lucky);
        }

    }
    public static void golemDefence () {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[6] > 0 && heroesHealth[i] > 0 && heroesHealth[6] != heroesHealth[i]) {
                heroesHealth[i] += bossDamage / 5;
                heroesHealth[6] -= bossDamage / 5; }
        } }
    public static void berserkShoot () {
        Random random = new Random();
        int randomDamage = random.nextInt(15) + 1;
        int randomC = random.nextInt(3) + 1;
        if (heroesHealth[7] > 0 && bossHealth > 0) {
            switch (randomC) {
                case 1:
                    heroesDamage[7] = (heroesDamage[7] + bossDamage) - randomDamage;
                    System.out.println("Берсерка урон критический");
                    System.out.println("Потери при увеличении урон у Берсерка " + randomDamage);
                    break;
                case 2:
                    bossDamage = 50;
                    break;
                case 3:
                    bossDamage = 50;
                    break;
            }
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        printStatistics();
        medicSkill();
        thorSkill();
        luckySkill();
        golemDefence();
        berserkShoot();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; //2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    criticalMessage = "Critical damage: " + damage;
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
        if (criticalMessage != null) {
            System.out.println(">>> " + criticalMessage);
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }
}
