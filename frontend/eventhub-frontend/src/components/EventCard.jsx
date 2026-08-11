import { Link } from 'react-router-dom';

export default function EventCard({ event }) {
    return (
        <div className="border border-gray-300 p-4 rounded-lg shadow-sm">
            <h2 className="text-xl font-bold">{event.title}</h2>
            <p className="text-sm text-gray-600">{event.category} | {event.venueName}</p>
            <p className="mt-2">{event.description}</p>
            
            {/* button that lead to details page of the event */}
            <Link to={`/events/${event.id}`}>
                <button className="mt-4 bg-blue-600 text-white px-4 py-2 rounded">
                    View Details / Reserve
                </button>
            </Link>
        </div>
    );
}