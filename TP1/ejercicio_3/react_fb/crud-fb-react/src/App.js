import './App.css';
import Show from './components/Show'
import Edit from './components/Edit'
import Create from './components/Create'

import { BrowserRouter, Route, Routes } from 'react-router-dom'

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Show/>}/>
          <Route path='/edit/:id' element={<Edit/>}/>
          <Route path='/create' element={<Create/>}/>
        </Routes>
      </BrowserRouter>

    </div>
  );
}

export default App;
