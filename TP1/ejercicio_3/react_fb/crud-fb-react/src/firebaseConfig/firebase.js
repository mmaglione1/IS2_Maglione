import { initializeApp } from "firebase/app";

import { getFirestore } from '@firebase/firestore'

const firebaseConfig = {
  apiKey: "AIzaSyD-YscXbtRyZEB01u-YKn7ut8YXew1hGeA",
  authDomain: "crud-react-7609.firebaseapp.com",
  projectId: "crud-react-7609",
  storageBucket: "crud-react-7609.firebasestorage.app",
  messagingSenderId: "114549457461",
  appId: "1:114549457461:web:24246f3e3801c3b4fa277c"
};

const app = initializeApp(firebaseConfig);

export const db = getFirestore(app)
