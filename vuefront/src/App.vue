<template>
  <div>
    <h1>개인정보 전송 페이지</h1>
    <form @submit.prevent="submitForm">
      <label for="name">이름:</label>
      <input type="text" id="name" v-model="formData.name" required>

      <label for="password">비밀번호:</label>
      <input type="password" id="password" v-model="formData.password" required>

      <button type="submit">전송</button>
      <!-- 다운로드 버튼 -->
      <a v-if="showDownloadButton" @click="downloadReport">다운로드</a>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formData: {
        name: '',
        password: ''
      },
      showDownloadButton: false, // 다운로드 버튼을 표시할지 여부를 결정하는 상태
      downloadLink: '' // 다운로드 링크
    };
  },
  methods: {
    submitForm() {
      const queryString = new URLSearchParams(this.formData).toString();
      
      fetch(`/api/report?${queryString}`)
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.blob();
        })
        .then(blob => {
          // 다운로드 링크가 아닌 파일을 다운로드할 수 있는 방식으로 변경
          this.showDownloadButton = true; // 다운로드 버튼 활성화
          this.blob = blob; // 다운로드할 파일(blob) 저장
        })
        .catch(error => {
          console.error('There has been a problem with your fetch operation:', error.message);
        });
    },
    downloadReport() {
      // 다운로드할 파일(blob)을 사용하여 다운로드 시작
      const url = window.URL.createObjectURL(new Blob([this.blob]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'report.pdf');
      document.body.appendChild(link);
      link.click();
    }
  }
};
</script>

<style scoped>
/* 스타일 작성 */
</style>
