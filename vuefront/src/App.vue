<template>
  <div>
    <h1>개인정보 전송 페이지</h1>
    <form @submit.prevent="submitForm">
      <label for="name">이름:</label>
      <input type="text" id="name" v-model="formData.name" required>

      <label for="password">비밀번호:</label>
      <input type="password" id="password" v-model="formData.password" required>

      <button type="submit">전송</button>
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
      }
    };
  },
  methods: {
    submitForm() {
  // GET 요청을 보낼 때는 URL에 쿼리 매개변수를 추가하여 데이터를 전달합니다.
  // FormData 객체를 query string으로 직렬화하여 전달합니다.
  const queryString = new URLSearchParams(this.formData).toString();
  
  fetch(`/api/report?${queryString}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.text(); // 응답 데이터를 텍스트로 변환하여 반환
    })
    .then(data => {
      console.log(data); // 서버로부터 받은 응답 데이터 출력
      // 여기에 적절한 처리를 추가하세요 (예: 응답에 따른 화면 처리 등)
    })
    .catch(error => {
      console.error('There has been a problem with your fetch operation:', error.message);
      // 여기에 오류 처리를 추가하세요 (예: 사용자에게 오류 메시지 표시 등)
    });
}

  }
};
</script>

<style scoped>
/* 스타일 작성 */
</style>
