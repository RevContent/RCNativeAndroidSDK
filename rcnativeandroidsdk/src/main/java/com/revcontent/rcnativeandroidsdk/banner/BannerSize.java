package com.revcontent.rcnativeandroidsdk.banner;

public enum BannerSize {
    W160XH600(160, 600),
    W240XH400(240,400),
    W250XH250(250,250),
    W300XH50(300,50),
    W300XH250(300,250),
    W300XH600(300,600),
    W300XH1050(300,1050),
    W320XH50(320,50),
    W336XH280(336,280),
    W640XH1136(640,1136),
    W728XH90(728,90),
    W720XH300(720,300),
    W750XH1334(750,1334),
    W970XH250(970,250),
    W970XH500(970,500),
    W1080XH1920(1080,1920);

    public final Integer width;
    public final Integer height;

    BannerSize(Integer width, Integer height){
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return width + "x" + height;
    }
}
