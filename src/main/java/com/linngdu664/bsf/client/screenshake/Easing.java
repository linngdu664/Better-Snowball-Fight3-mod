package com.linngdu664.bsf.client.screenshake;

import java.util.HashMap;

public abstract class Easing {
    public static final HashMap<String, Easing> EASINGS = new HashMap<>();
    public final String name;
    public static final Easing LINEAR = new Easing("linear") {
        public float ease(float t, float b, float c, float d) {
            return c * t / d + b;
        }
    };
    public static final Easing QUAD_IN = new Easing("quadIn") {
        public float ease(float t, float b, float c, float d) {
            return c * (t /= d) * t + b;
        }
    };
    public static final Easing QUAD_OUT = new Easing("quadOut") {
        public float ease(float t, float b, float c, float d) {
            return -c * (t /= d) * (t - 2.0F) + b;
        }
    };
    public static final Easing QUAD_IN_OUT = new Easing("quadInOut") {
        public float ease(float t, float b, float c, float d) {
            return (t /= d / 2.0F) < 1.0F ? c / 2.0F * t * t + b : -c / 2.0F * (--t * (t - 2.0F) - 1.0F) + b;
        }
    };
    public static final Easing CUBIC_IN = new Easing("cubicIn") {
        public float ease(float t, float b, float c, float d) {
            return c * (t /= d) * t * t + b;
        }
    };
    public static final Easing CUBIC_OUT = new Easing("cubicOut") {
        public float ease(float t, float b, float c, float d) {
            return c * ((t = t / d - 1.0F) * t * t + 1.0F) + b;
        }
    };
    public static final Easing CUBIC_IN_OUT = new Easing("cubicInOut") {
        public float ease(float t, float b, float c, float d) {
            return (t /= d / 2.0F) < 1.0F ? c / 2.0F * t * t * t + b : c / 2.0F * ((t -= 2.0F) * t * t + 2.0F) + b;
        }
    };
    public static final Easing QUARTIC_IN = new Easing("quarticIn") {
        public float ease(float t, float b, float c, float d) {
            return c * (t /= d) * t * t * t + b;
        }
    };
    public static final Easing QUARTIC_OUT = new Easing("quarticOut") {
        public float ease(float t, float b, float c, float d) {
            return -c * ((t = t / d - 1.0F) * t * t * t - 1.0F) + b;
        }
    };
    public static final Easing QUARTIC_IN_OUT = new Easing("quarticInOut") {
        public float ease(float t, float b, float c, float d) {
            return (t /= d / 2.0F) < 1.0F ? c / 2.0F * t * t * t * t + b : -c / 2.0F * ((t -= 2.0F) * t * t * t - 2.0F) + b;
        }
    };
    public static final Easing QUINTIC_IN = new Easing("quinticIn") {
        public float ease(float t, float b, float c, float d) {
            return c * (t /= d) * t * t * t * t + b;
        }
    };
    public static final Easing QUINTIC_OUT = new Easing("quinticOut") {
        public float ease(float t, float b, float c, float d) {
            return c * ((t = t / d - 1.0F) * t * t * t * t + 1.0F) + b;
        }
    };
    public static final Easing QUINTIC_IN_OUT = new Easing("quinticInOut") {
        public float ease(float t, float b, float c, float d) {
            return (t /= d / 2.0F) < 1.0F ? c / 2.0F * t * t * t * t * t + b : c / 2.0F * ((t -= 2.0F) * t * t * t * t + 2.0F) + b;
        }
    };
    public static final Easing SINE_IN = new Easing("sineIn") {
        public float ease(float t, float b, float c, float d) {
            return -c * (float)Math.cos((double)(t / d) * 1.5707963267948966) + c + b;
        }
    };
    public static final Easing SINE_OUT = new Easing("sineOut") {
        public float ease(float t, float b, float c, float d) {
            return c * (float)Math.sin((double)(t / d) * 1.5707963267948966) + b;
        }
    };
    public static final Easing SINE_IN_OUT = new Easing("sineInOut") {
        public float ease(float t, float b, float c, float d) {
            return -c / 2.0F * ((float)Math.cos(Math.PI * (double)t / (double)d) - 1.0F) + b;
        }
    };
    public static final Easing EXPO_IN = new Easing("expoIn") {
        public float ease(float t, float b, float c, float d) {
            return t == 0.0F ? b : c * (float)Math.pow(2.0, 10.0F * (t / d - 1.0F)) + b;
        }
    };
    public static final Easing EXPO_OUT = new Easing("expoOut") {
        public float ease(float t, float b, float c, float d) {
            return t == d ? b + c : c * (-((float)Math.pow(2.0, -10.0F * t / d)) + 1.0F) + b;
        }
    };
    public static final Easing EXPO_IN_OUT = new Easing("expoInOut") {
        public float ease(float t, float b, float c, float d) {
            if (t == 0.0F) {
                return b;
            } else if (t == d) {
                return b + c;
            } else {
                return (t /= d / 2.0F) < 1.0F ? c / 2.0F * (float)Math.pow(2.0, 10.0F * (t - 1.0F)) + b : c / 2.0F * (-((float)Math.pow(2.0, -10.0F * --t)) + 2.0F) + b;
            }
        }
    };
    public static final Easing CIRC_IN = new Easing("circIn") {
        public float ease(float t, float b, float c, float d) {
            return -c * ((float)Math.sqrt(1.0F - (t /= d) * t) - 1.0F) + b;
        }
    };
    public static final Easing CIRC_OUT = new Easing("circOut") {
        public float ease(float t, float b, float c, float d) {
            return c * (float)Math.sqrt(1.0F - (t = t / d - 1.0F) * t) + b;
        }
    };
    public static final Easing CIRC_IN_OUT = new Easing("circInOut") {
        public float ease(float t, float b, float c, float d) {
            return (t /= d / 2.0F) < 1.0F ? -c / 2.0F * ((float)Math.sqrt(1.0F - t * t) - 1.0F) + b : c / 2.0F * ((float)Math.sqrt(1.0F - (t -= 2.0F) * t) + 1.0F) + b;
        }
    };
    public static final Easing.Elastic ELASTIC_IN = new Easing.ElasticIn();
    public static final Easing.Elastic ELASTIC_OUT = new Easing.ElasticOut();
    public static final Easing.Elastic ELASTIC_IN_OUT = new Easing.ElasticInOut();
    public static final Easing.Back BACK_IN = new Easing.BackIn();
    public static final Easing.Back BACK_OUT = new Easing.BackOut();
    public static final Easing.Back BACK_IN_OUT = new Easing.BackInOut();
    public static final Easing BOUNCE_IN = new Easing("bounceIn") {
        public float ease(float t, float b, float c, float d) {
            return c - Easing.BOUNCE_OUT.ease(d - t, 0.0F, c, d) + b;
        }
    };
    public static final Easing BOUNCE_OUT = new Easing("bounceOut") {
        public float ease(float t, float b, float c, float d) {
            if ((t /= d) < 0.36363637F) {
                return c * 7.5625F * t * t + b;
            } else if (t < 0.72727275F) {
                return c * (7.5625F * (t -= 0.54545456F) * t + 0.75F) + b;
            } else {
                return t < 0.90909094F ? c * (7.5625F * (t -= 0.8181818F) * t + 0.9375F) + b : c * (7.5625F * (t -= 0.95454544F) * t + 0.984375F) + b;
            }
        }
    };
    public static final Easing BOUNCE_IN_OUT = new Easing("bounceInOut") {
        public float ease(float t, float b, float c, float d) {
            return t < d / 2.0F ? Easing.BOUNCE_IN.ease(t * 2.0F, 0.0F, c, d) * 0.5F + b : Easing.BOUNCE_OUT.ease(t * 2.0F - d, 0.0F, c, d) * 0.5F + c * 0.5F + b;
        }
    };

    public Easing(String name) {
        this.name = name;
        EASINGS.put(name, this);
    }

    public static Easing valueOf(String name) {
        return EASINGS.get(name);
    }

    public abstract float ease(float var1, float var2, float var3, float var4);

    public static class ElasticIn extends Easing.Elastic {
        public ElasticIn(float amplitude, float period) {
            super("elasticIn", amplitude, period);
        }

        public ElasticIn() {
            super("elasticIn");
        }

        public float ease(float t, float b, float c, float d) {
            float a = this.getAmplitude();
            float p = this.getPeriod();
            if (t == 0.0F) {
                return b;
            } else if ((t /= d) == 1.0F) {
                return b + c;
            } else {
                if (p == 0.0F) {
                    p = d * 0.3F;
                }

                float s;
                if (a < Math.abs(c)) {
                    a = c;
                    s = p / 4.0F;
                } else {
                    s = p / 6.2831855F * (float)Math.asin(c / a);
                }

                return -(a * (float)Math.pow(2.0, 10.0F * --t) * (float)Math.sin((double)(t * d - s) * 6.283185307179586 / (double)p)) + b;
            }
        }
    }

    public abstract static class Elastic extends Easing {
        private float amplitude;
        private float period;

        public Elastic(String name, float amplitude, float period) {
            super(name);
            this.amplitude = amplitude;
            this.period = period;
        }

        public Elastic(String name) {
            this(name, -1.0F, 0.0F);
        }

        public float getPeriod() {
            return this.period;
        }

        public void setPeriod(float period) {
            this.period = period;
        }

        public float getAmplitude() {
            return this.amplitude;
        }

        public void setAmplitude(float amplitude) {
            this.amplitude = amplitude;
        }
    }

    public static class ElasticOut extends Easing.Elastic {
        public ElasticOut(float amplitude, float period) {
            super("elasticOut", amplitude, period);
        }

        public ElasticOut() {
            super("elasticOut");
        }

        public float ease(float t, float b, float c, float d) {
            float a = this.getAmplitude();
            float p = this.getPeriod();
            if (t == 0.0F) {
                return b;
            } else if ((t /= d) == 1.0F) {
                return b + c;
            } else {
                if (p == 0.0F) {
                    p = d * 0.3F;
                }

                float s;
                if (a < Math.abs(c)) {
                    a = c;
                    s = p / 4.0F;
                } else {
                    s = p / 6.2831855F * (float)Math.asin(c / a);
                }

                return a * (float)Math.pow(2.0, -10.0F * t) * (float)Math.sin((double)(t * d - s) * 6.283185307179586 / (double)p) + c + b;
            }
        }
    }

    public static class ElasticInOut extends Easing.Elastic {
        public ElasticInOut(float amplitude, float period) {
            super("elasticInOut", amplitude, period);
        }

        public ElasticInOut() {
            super("elasticInOut");
        }

        public float ease(float t, float b, float c, float d) {
            float a = this.getAmplitude();
            float p = this.getPeriod();
            if (t == 0.0F) {
                return b;
            } else if ((t /= d / 2.0F) == 2.0F) {
                return b + c;
            } else {
                if (p == 0.0F) {
                    p = d * 0.45000002F;
                }

                float s;
                if (a < Math.abs(c)) {
                    a = c;
                    s = p / 4.0F;
                } else {
                    s = p / 6.2831855F * (float)Math.asin(c / a);
                }

                return t < 1.0F ? -0.5F * a * (float)Math.pow(2.0, 10.0F * --t) * (float)Math.sin((double)(t * d - s) * 6.283185307179586 / (double)p) + b : a * (float)Math.pow(2.0, -10.0F * --t) * (float)Math.sin((double)(t * d - s) * 6.283185307179586 / (double)p) * 0.5F + c + b;
            }
        }
    }

    public static class BackIn extends Easing.Back {
        public BackIn() {
            super("backIn");
        }

        public BackIn(float overshoot) {
            super("backIn", overshoot);
        }

        public float ease(float t, float b, float c, float d) {
            float s = this.getOvershoot();
            return c * (t /= d) * t * ((s + 1.0F) * t - s) + b;
        }
    }

    public abstract static class Back extends Easing {
        public static final float DEFAULT_OVERSHOOT = 1.70158F;
        private float overshoot;

        public Back(String name) {
            this(name, 1.70158F);
        }

        public Back(String name, float overshoot) {
            super(name);
            this.overshoot = overshoot;
        }

        public void setOvershoot(float overshoot) {
            this.overshoot = overshoot;
        }

        public float getOvershoot() {
            return this.overshoot;
        }
    }

    public static class BackOut extends Easing.Back {
        public BackOut() {
            super("backOut");
        }

        public BackOut(float overshoot) {
            super("backOut", overshoot);
        }

        public float ease(float t, float b, float c, float d) {
            float s = this.getOvershoot();
            return c * ((t = t / d - 1.0F) * t * ((s + 1.0F) * t + s) + 1.0F) + b;
        }
    }

    public static class BackInOut extends Easing.Back {
        public BackInOut() {
            super("backInOut");
        }

        public BackInOut(float overshoot) {
            super("backInOut", overshoot);
        }

        public float ease(float t, float b, float c, float d) {
            float s = this.getOvershoot();
            return (t /= d / 2.0F) < 1.0F ? c / 2.0F * t * t * (((s = (float)((double)s * 1.525)) + 1.0F) * t - s) + b : c / 2.0F * ((t -= 2.0F) * t * (((s = (float)((double)s * 1.525)) + 1.0F) * t + s) + 2.0F) + b;
        }
    }
}
