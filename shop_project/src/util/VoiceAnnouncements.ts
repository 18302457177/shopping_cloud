class VoiceAnnouncements {
    public synth = window.speechSynthesis // 启用文本
    public msg: any = new SpeechSynthesisUtterance()
    public language: string = 'zh-CN'  // 使用的语言:中文
    public volume: number = 1 // 音量
    public speed: number = 1  // 语速
    public pitch: number = 1 // 音高
 
    // 开始语音提示
    startVoiceFunction = (content: String) => {
        this.msg.text = content
        this.msg.language = this.language 
        this.msg.volume = this.volume 
        this.msg.speed = this.speed
        this.msg.pitch = this.pitch
        this.synth.speak(this.msg) 
    }
    // 停止语音提示
    stopVoiceFunction = (content: any) => {
        this.msg.text = content
        this.msg. language = this.language
        this.synth.cancel()
    }
 
}
 
//传出实例，保证整个系统只存在单例的Voice
const VoiceAnnouncementsInstance = new VoiceAnnouncements()
 
export default VoiceAnnouncementsInstance