import { Message } from 'tdesign-mobile-vue'

export const showMessage = (theme: string, content, duration = 5000) => {
  if (Message[theme]) {
    Message[theme]({
      offset: [60, 100],
      content,
      duration,
      icon: true,
      zIndex: 20000,
      context: document.querySelector('#message')
    })
  }
}
