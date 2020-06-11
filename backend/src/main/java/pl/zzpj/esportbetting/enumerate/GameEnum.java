package pl.zzpj.esportbetting.enumerate;

public enum GameEnum {
    LEAGUE_OF_LEGENDS {
        public String toString() {
            return "lol";
        }
    },
    CS_GO {
        public String toString() {
            return "csgo";
        }
    },
    DOTA2 {
        public String toString() {
            return "dota2";
        }
    },
    OVERWATCH {
        public String toString() {
            return "ow";
        }
    },
}
