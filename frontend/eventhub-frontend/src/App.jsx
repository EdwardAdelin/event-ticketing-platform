import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar'; 
import HomePage from './pages/HomePage';
// TO BE ADDED import EventDetailsPage from './pages/EventDetailsPage';
// TO BE ADDED import LoginPage from './pages/LoginPage';

function App() {
    return (
        <Router>
            {/* Navbar visible on all pages */}
            <Navbar /> 
            
            {/* direct routes controlling wich page renders based on url */}
            <div className="container mx-auto">
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    {/*TO SOLVE <Route path="/events/:id" element={<EventDetailsPage />} /> */}
                    {/*TO SOLVE <Route path="/login" element={<LoginPage />} /> */}
                </Routes>
            </div>
        </Router>
    );
}

export default App;