# CodingHedgehog (코딩하는 고슴도치)
### __안현진,배선영,이상현,양소영__

#### 문서 관리 : slack
#### 일정 관리 : trello

## 데이터
----------------------------
### 1. 데이터 수집
- 기본 데이터
  - 공공 API를 통해 데이터 수집
  - public_api 폴더의 코드 사용
- 테스트 데이터
  - public_api/download_test.py 참고
- 상세 데이터
  - 약의 효능,복용방법에 대한 데이터 수집

### 2. 데이터 라벨링
- 딥러닝
  - api 데이터에서 내용 탐색 후 자동 라벨링
- 객체 탐지
  - object 영역 labelImg 도구로 라벨링
  - [labelImg](https://github.com/tzutalin/labelImg)

### 3. 이미지 편집
1. 객체 탐지를 통해 알약 객체 찾기
2. 객체 영역의 좌표값 저장
3. openCV를 통해 해당 좌표값을 기준으로 사진 자르기

## 딥러닝
----------------------------
### 1. 모양 학습
### 2. 색상 학습
### 3. 문자 학습
### 4. 객체 탐지
1. 학습
    - [Darkflow](https://junyoung-jamong.github.io/deep/learning/2019/01/22/Darkflow%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%B4-YOLO%EB%AA%A8%EB%8D%B8-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EB%94%94%ED%85%8D%EC%85%98-%EA%B5%AC%ED%98%84-in-windows.html)
    - predefined_classes.txt,labels.txt 내용 Pill로 변경
    - tiny-yolo.cfg 내용 중 filters=30/classes=1로 변경
    - data 디렉토리 아래에 annotations,dataset 디렉토리를 생성하고 라벨링된 데이터를 넣어줌
    - 명령어를 통해 학습시켜주고 TensorBoard를 통해 loss의 수렴정도를 확인함
2. detection
    - [Object Detection](https://junyoung-jamong.github.io/machine/learning/2019/01/25/Android%EC%97%90%EC%84%9C-%EB%82%B4-YOLO%EB%AA%A8%EB%8D%B8-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0.html)
    - 학습 데이터의 *.pb파일를 assets에 추가해줌
    - DetectorActivity.java에서 파일 경로와 DetectorMode를 변경
    - TensorFlowYoloDetector.java에서 클래스 개수와 label명을 변경

## 웹서버
----------------------------
### 1. DB 설계
### 2. DB 구축
### 3. 서버 구축
### 4. 서버 통신

## 애플리케이션
----------------------------
### 1. UI 제작
- Material UI 사용
- PowerMockUp으로 스토리보드 작성

### 2. 기본기능 제작
- 객체 탐지 카메라 기능
- 복약 알림

### 3. 웹서버 통신
- 기본 검색 기능(텍스트로)

### 4. 사진 검색 기능

