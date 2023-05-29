package efs.task.oop;

public class ExtraordinaryVillager extends Villager {

    public ExtraordinaryVillager(String name, int age, Skill skill) {
        super(name, age);
        this.skill = skill;
    }
    public enum Skill {
        IDENTIFY("I will identify items for you at no charge."),
        SHELTER("I can offer you poor shelter");

        private final String skill;
        Skill(String skill) {
            this.skill = skill;
        }
        public String getSkill() {
            return this.skill;
        }
    }
    Skill skill;
    @Override
    public void sayHello() {
        System.out.println("Greetings traveler... I'm " + name + " and I'm " + age + " years old. " + skill.getSkill());
    }

    @Override
    public void attack(Fighter victim) {
        victim.takeHit(0);
    }

    @Override
    public void takeHit(int damage) {
        this.health = 0;
    }
}
