package de.dfki.vsm.players.stickman.animation.head;

import de.dfki.vsm.players.stickman.Stickman;
import de.dfki.vsm.players.stickman.animation.Animation;
import de.dfki.vsm.players.stickman.animation.BodyAnimation;
import java.util.ArrayList;

/**
 *
 * @author Patrick Gebhard
 *
 */
public class LookRight extends Animation {

	public LookRight(Stickman sm, int duration, boolean block) {
		super(sm, duration, block);
	}

	@Override
	public void playAnimation() {
		int translationUnit = 3;

		// look left
		mAnimationPart = new ArrayList<>();
		mAnimationPart.add(new BodyAnimation(mStickman.mLeftEye, "shape", "LOOKRIGHT"));
		mAnimationPart.add(new BodyAnimation(mStickman.mRightEye, "shape", "LOOKRIGHT"));
		playAnimationPart(20);

//		// blink up
//		mAnimationPart = new ArrayList<>();
//		mAnimationPart.add(new BodyAnimation(mStickman.mLeftEye, "shape", "DEFAULT"));
//		mAnimationPart.add(new BodyAnimation(mStickman.mRightEye, "shape", "DEFAULT"));
//		playAnimationPart(2);
	}
}