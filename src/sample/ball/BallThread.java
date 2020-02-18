package sample.ball;

/**
 * @author langjr
 * @version 1.0
 * @created 22-Jan-2020 3:21:42 PM
 */
public class BallThread extends Thread {

	BallItem m_BallItem;
	PaddleItem left;
	PaddleItem right;
	private boolean passed = false;
	private boolean modified = false;

	volatile boolean loaded = true;

	private int frameCount = 0;
	double cachedSpeed;
	private double lastIndex = 6;
	private boolean riding = false;
	private int buffer = 0;

	public void run() {
		try {
			while (loaded) {
				sleep(16, 600000);
				while (!m_BallItem.update) {
					sleep(0, 100);
				}
				if (m_BallItem.altBehavior && m_BallItem.index == 3) {
					if (frameCount < 7) {
						m_BallItem.speed = 2 * cachedSpeed;
					} else if (frameCount < 20) {
						m_BallItem.speed = 0.5 * cachedSpeed;
						if (frameCount == 19) {
							frameCount = 0;
						}
					}
				}
				frameCount++;
				double dist = BallItem.DEFAULT_DIST * m_BallItem.speed * m_BallItem.direction;
				m_BallItem.nextX = m_BallItem.getCenterX() + dist * Math.cos(m_BallItem.angle);
				m_BallItem.nextY = m_BallItem.getCenterY() + dist * Math.sin(m_BallItem.angle);
				if (modified && ++buffer == 3) {
					modified = false;
					buffer = 0;
				}
				checkCollision();
				m_BallItem.update = false;
			}
		} catch (InterruptedException e) {
			System.out.println("F");
		}
	}

	private void checkCollision() {
		if (m_BallItem.nextX <= 65) {
			double y = left.getY();
			if (m_BallItem.nextY - 15 <= (y + 80) && m_BallItem.nextY + 15 >= y && !passed) {
				m_BallItem.nextX = 65;
				m_BallItem.angle = (Math.PI / 4.0) * m_BallItem.random.nextDouble();
				m_BallItem.direction *= -1;
				m_BallItem.speed += m_BallItem.random.nextDouble() / 6;
				lastIndex = m_BallItem.index;
				generateIndex();
				if (m_BallItem.altBehavior) {
					if (m_BallItem.index == 0) {
						m_BallItem.direction *= -1;
						m_BallItem.nextX = 535;
					} else if (m_BallItem.index == 3) {
						cachedSpeed = m_BallItem.speed;
						frameCount = 0;
					} else if (m_BallItem.index == 2) {
						left.invert();
						right.invert();
					}
				}
				m_BallItem.reflect = true;
			} else {
				passed = true;
			}
		} else if (m_BallItem.nextX >= 535) {
			double y = right.getY();
			if (m_BallItem.nextY - 15 <= (y + 80) && m_BallItem.nextY + 15 >= y && !passed) {
				m_BallItem.nextX = 535;
				m_BallItem.angle = (Math.PI / 4.0) * m_BallItem.random.nextDouble();
				m_BallItem.direction *= -1;
				m_BallItem.speed += m_BallItem.random.nextDouble() / 6;
				lastIndex = m_BallItem.index;
				generateIndex();
				if (m_BallItem.altBehavior) {
					if (m_BallItem.index == 0) {
						m_BallItem.direction *= -1;
						m_BallItem.nextX = 65;
					} else if (m_BallItem.index == 3) {
						cachedSpeed = m_BallItem.speed;
						frameCount = 0;
					} else if (m_BallItem.index == 2) {
						left.invert();
						right.invert();
					}
				}
				m_BallItem.reflect = true;
			} else {
				passed = true;
			}
		}
		if (m_BallItem.nextY <= 15) {
			if (m_BallItem.altBehavior) {
				if (m_BallItem.index == 1) m_BallItem.nextY = 385;
				else if (m_BallItem.index == 4 && !riding) {
					m_BallItem.nextY = 15;
					riding = true;
					frameCount = 0;
				} else {
					if (riding) {
						m_BallItem.nextY = 15;
						if (++frameCount == 30) {
							frameCount = 0;
							riding = false;
						}
					}
					if (!riding) {
						m_BallItem.nextY = 15;
						m_BallItem.angle = m_BallItem.angle + ((Math.PI / 2) * m_BallItem.direction);
					}
				}
			} else {
				m_BallItem.nextY = 15;
				m_BallItem.angle = m_BallItem.angle + ((Math.PI / 2) * m_BallItem.direction);
			}
		} else if (m_BallItem.nextY >= 385) {
			if (m_BallItem.altBehavior) {
				if (m_BallItem.index == 1) m_BallItem.nextY = 15;
				else if (m_BallItem.index == 4 && !riding) {
					m_BallItem.nextY = 385;
					riding = true;
					frameCount = 0;
				} else {
					if (riding) {
						m_BallItem.nextY = 385;
						if (++frameCount == 30) {
							frameCount = 0;
							riding = false;
						}
					}
					if (!riding) {
						m_BallItem.nextY = 385;
						m_BallItem.angle = m_BallItem.angle - ((Math.PI / 2) * m_BallItem.direction);
					}
				}
			} else {
				m_BallItem.nextY = 385;
				m_BallItem.angle = m_BallItem.angle - ((Math.PI / 2) * m_BallItem.direction);
			}
		}
		if (m_BallItem.altBehavior) {
			if (m_BallItem.index == 5) {
				if (m_BallItem.nextY <= 150) {
					m_BallItem.nextY = 150;
					if (!modified && buffer == 0) {
						m_BallItem.angle = m_BallItem.angle + ((Math.PI / 2) * m_BallItem.direction);
					}
				} else if (m_BallItem.nextY >= 250) {
					m_BallItem.nextY = 250;
					if (!modified && buffer == 0) {
						m_BallItem.angle = m_BallItem.angle - ((Math.PI / 2) * m_BallItem.direction);
					}
				}
			}
			if (lastIndex == 3) {
				m_BallItem.speed = cachedSpeed;
				lastIndex = 6;
			} else if (lastIndex == 2) {
				left.invert();
				right.invert();
				lastIndex = 6;
			} else if (lastIndex == 4) {
				if (!(m_BallItem.index == 5) || !modified) {
					if (riding) {
						riding = false;
						m_BallItem.angle = m_BallItem.angle - ((Math.PI / 2) * m_BallItem.direction);
					}
					lastIndex = 6;
				}
			}
		}
	}

	private void generateIndex() {
		int newIndex = m_BallItem.random.nextInt(6);
		while (newIndex == m_BallItem.index) {
			newIndex = m_BallItem.random.nextInt(6);
		}
		m_BallItem.index = newIndex;
		modified = true;
	}
}
