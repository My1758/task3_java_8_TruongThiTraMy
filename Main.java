package efs.task.oop;

import java.util.Random;

interface Fighter {
    void attack(Fighter victim);
    void takeHit(int damage);
    boolean isDead();
}

abstract class Monster implements Fighter {
    protected int health;
    protected int damage;
    protected boolean isDead;

    public Monster(int health, int damage) {
        this.health = health;
        this.damage = damage;
        this.isDead=false;
    }
    public void setHealth(int health){
        this.health=health;
    }
    public void setDamage(int damage){
        this.damage=damage;
    }
    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }
    public boolean isDead(){
        return isDead;
    }
}

class Monsters {
    static final Monster andariel = new Monster(10, 70) {
        @Override
        public void attack(Fighter victim) {
            victim.takeHit(damage);
        }

        @Override
        public void takeHit(int damage) {
            if (!isDead) {
                health -= damage;
                Monsters.monstersHealth -= damage;
                if (health <= 0 && monstersHealth <= 0) {
                    health = 0;
                    monstersHealth=0;
                    isDead = true;
                }
            }
        }
    };

    static final Monster blacksmith = new Monster(100, 25) {
        @Override
        public void attack(Fighter victim) {
            victim.takeHit(damage);
        }

        @Override
        public void takeHit(int damage) {
            health -= (5 + damage);
            Monsters.monstersHealth -= (5 + damage);
        }
    };

    static int monstersHealth = andariel.getHealth() + blacksmith.getHealth();
}


class Villager implements Fighter {
    private String name;
    private int age;
    private int health;
    private boolean isDead;

    public Villager(String name, int age) {
        this.name = name;
        this.age = age;
        this.health=100;
        this.isDead=false;
    }
    public void setName(String name){
        this.name=name;
    }
    public boolean isDead(){
        return isDead;
    }
    public String getName(){
        return name;
    }
    public void setAge(int age){
        this.age=age;
    }
    public int getAge(){
        return age;
    }
    public int getHealth(){
        return health;
    }
    public void attack(Fighter victim) {
        int damage = (int) ((100 - age * 0.5) / 10);
        victim.takeHit(damage);
    }

    @Override
    public void takeHit(int damage) {
        if (!isDead) {
            health -= damage;
            if (health <= 0) {
                health = 0;
                isDead = true;
            }
        }
    }
    public void sayHello() {
        System.out.println("Greetings traveler... I'm " + name + " and I'm " + age + " years old.");
    }
}

class ExtraordinaryVillager  extends Villager{
    public enum Skill {
        IDENTIFY("I will identify items for you at no charge."),
        SHELTER("I can offer you poor shelter.");

        private String description;

        Skill(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    private Skill skill;
    private boolean isDead;
    public ExtraordinaryVillager(String name, int age, Skill skill) {
        super(name, age);
        this.skill = skill;
        this.isDead=false;
        
    }
    public void attack(Fighter victim) {
        // ExtraordinaryVillagers don't attack
    }

    @Override
    public void takeHit(int damage) {
        // ExtraordinaryVillagers die from one hit
       // super.takeHit(damage);
        isDead = true;
        
    }
    

    @Override
    public void sayHello() {
        System.out.println("Greetings traveler... I'm " + getName() + " and I'm " + getAge() + " years old. " + skill.getDescription());
    }
}

public class Main {
    public static void main(String[] args) {
        Villager villager1 = new Villager("Kashya", 30);
        //Villager villager2 = new Villager("Akara", 40);
        ExtraordinaryVillager akara = new ExtraordinaryVillager("Akara", 40, ExtraordinaryVillager.Skill.SHELTER);
        Villager villager3 = new Villager("Gheed", 50);
        //Villager villager4 = new Villager("Deckard Cain", 85);
        ExtraordinaryVillager deckardCain = new ExtraordinaryVillager("Deckard Cain", 85, ExtraordinaryVillager.Skill.IDENTIFY);
        Villager villager5 = new Villager("Warriv", 35);
        Villager villager6 = new Villager("Flawia", 25);

        villager1.sayHello();
       // villager2.sayHello();
       akara.sayHello();
        villager3.sayHello();
       // villager4.sayHello();
       deckardCain.sayHello();
        villager5.sayHello();
        villager6.sayHello();
        
        Object objectDeckardCain = deckardCain;
        Object objectAkara = akara;

        
        Random random = new Random();
        boolean villagersTurn = true;

        while (Monsters.andariel.getHealth() > 0 || Monsters.blacksmith.getHealth() > 0) {
            if (villagersTurn) {
                for (Fighter villager : new Fighter[]{villager1, akara, villager3, deckardCain, villager5, villager6}) {
                    if (!villager.isDead()) {
                        villager.attack(Monsters.andariel);
                        villager.attack(Monsters.blacksmith);
                    }
                }
            } else {
                if (!Monsters.andariel.isDead()) {
                    Monsters.andariel.attack(deckardCain);
                    Monsters.andariel.attack(akara);
                }

                if (!Monsters.blacksmith.isDead()) {
                    Monsters.blacksmith.attack(deckardCain);
                    Monsters.blacksmith.attack(akara);
                }
            }

            villagersTurn = !villagersTurn;

            System.out.println("Potwory posiadaja jeszcze " + (Monsters.andariel.getHealth() + Monsters.blacksmith.getHealth()) + " punkty zycia");

            boolean anyVillagerAlive = false;
            for (Fighter villager : new Fighter[]{villager1, akara, villager3, deckardCain, villager5, villager6}) {
                if (!villager.isDead()) {
                    System.out.println("Aktualnie walczacy osadnik to " + ((Villager) villager).getName());
                    anyVillagerAlive = true;
                }
            }

            if (!anyVillagerAlive) {
                System.out.println("Obozowisko zostalo zniszczone!");
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (Monsters.andariel.getHealth() <= 0 && Monsters.blacksmith.getHealth() <= 0) {
            System.out.println("Obozowisko ocalone!");
        }

        deckardCain = (ExtraordinaryVillager) deckardCain;
        akara = (ExtraordinaryVillager) akara;

        // Sprawdź, czy można wywołać metody z klasy ExtraordinaryVillager
        deckardCain.attack(Monsters.andariel);
        akara.takeHit(10);
    }
}


