public class MoveOnlyEnemy extends Entity{
	public static String TYPE = "MoveOnlyEnemy";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "Enemy.png";
	
	private int xDelta = 1;
	private int yDelta = 1;
	private int offsetX;
	private int offsetY;
	private int totalXDelta;
	private int totalYDelta;
	
	public MoveOnlyEnemy(int totalXDelta, int totalYDelta) {
		super(MoveOnlyEnemy.TYPE, MoveOnlyEnemy.MAX_HEALTH, MoveOnlyEnemy.IMAGE_FILE);
		this.totalXDelta = totalXDelta;
		this.totalYDelta = totalYDelta;
	}
	
	@Override
	public void cycle(Game game) {
		if (game.getFrameCount() % 3 == 0) {
			offsetX += xDelta;
			offsetY += yDelta;
			updateYBy(xDelta);
			updateYBy(yDelta);
			if (offsetY >= totalYDelta || offsetY <= 0) {
				yDelta *= -1;
			}
			if (offsetX >= totalXDelta || offsetX <= 0) {
				xDelta *= -1;
			}
		}
	}
}
