package efs.task.oop;

public class Monsters {
    static final Monster andariel = new Monster(10, 70) {
        @Override
        public void attack(Fighter victim) {
            victim.takeHit(this.getDamage());
        }
        @Override
        public void takeHit(int damage) {
            this.setHealth(this.getHealth() - damage);
            monstersHealth -= damage;
        }
    };
    static final Monster blacksmith = new Monster(100, 25) {
        @Override
        public void attack(Fighter victim) {
            victim.takeHit(this.getDamage());
        }
        @Override
        public void takeHit(int damage) {
            this.setHealth(this.getHealth() - (5 + damage));
            monstersHealth -= (5 + damage);
        }
    };
    private static int monstersHealth = andariel.getHealth() + blacksmith.getHealth();
    public static int getMonstersHealth() {
        return monstersHealth;
    }
}
