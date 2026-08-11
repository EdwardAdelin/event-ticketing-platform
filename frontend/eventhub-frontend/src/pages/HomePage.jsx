import { useEffect, useState } from 'react';
import api from '../api/axios';
import EventCard from '../components/EventCard';

export default function HomePage() {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        // axios instance is represented by "api" name
        api.get('/events')
            .then(response => {
                setEvents(response.data);
                setLoading(false);
            })
            .catch(err => {
                setError(err.response?.data?.message || 'Error loading events');
                setLoading(false);
            });
    }, []);

    if (loading) return <h2>Events are loading...</h2>;
    if (error) return <h2>Error: {error}</h2>;

    return (
        <div className="p-6">
            <h1 className="text-3xl font-bold mb-6">Available events</h1>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                {events.map(event => (
                    <EventCard key={event.id} event={event} />
                ))}
            </div>
        </div>
    );
}