export default class LevelToIconMapper {
    static getUrl(level) {

        if (level < 5) {
            return require('../assets/pepega.png')
        }

        if (level < 10) {
            return require('../assets/poggies.png')
        }

        return require('../assets/ez.png')

    }
}