import { useEffect, useState } from 'react'

function App() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Funcția care apelează endpoint-ul tău REST
  useEffect(() => {
    fetch('http://localhost:8080/api/events')
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        setEvents(data);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <h2>Se încarcă evenimentele...</h2>;
  if (error) return <h2>Eroare: {error}</h2>;

  return (
    <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
      <h1>EventHub - Evenimente Disponibile</h1>
      
      {events.length === 0 ? (
        <p>Nu există evenimente momentan.</p>
      ) : (
        <div style={{ display: 'grid', gap: '20px', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))' }}>
          
          {events.map(event => (
            <div key={event.id} style={{ border: '1px solid #ccc', padding: '15px', borderRadius: '8px' }}>
              <h2>{event.title}</h2>
              <p><strong>Categorie:</strong> {event.category}</p>
              <p><strong>Locație:</strong> {event.venueName}</p>
              <p><strong>Data:</strong> {new Date(event.date).toLocaleDateString()}</p>
              <p>{event.description}</p>
              <button style={{ padding: '10px', backgroundColor: '#007BFF', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer'}}>
                Rezervă Bilete
              </button>
            </div>
          ))}

        </div>
      )}
    </div>
  )
}

export default App