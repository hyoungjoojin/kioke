import React from 'react';

const WeatherWidget = () => {
  return <div className='h-full w-full bg-black'></div>;
  // Mock data, could be replaced by props or fetched data
  const weather = {
    location: 'San Francisco',
    condition: 'Sunny',
    temperature: 72,
    humidity: 60,
    wind: 5,
  };

  // Simple SVG icon for sun
  const SunIcon = (
    <svg width='40' height='40' viewBox='0 0 40 40' fill='none'>
      <circle cx='20' cy='20' r='12' fill='#FFD700' />
      <g stroke='#FFD700' strokeWidth='2'>
        <line x1='20' y1='3' x2='20' y2='10' />
        <line x1='20' y1='30' x2='20' y2='37' />
        <line x1='3' y1='20' x2='10' y2='20' />
        <line x1='30' y1='20' x2='37' y2='20' />
        <line x1='8' y1='8' x2='13' y2='13' />
        <line x1='27' y1='27' x2='32' y2='32' />
        <line x1='32' y1='8' x2='27' y2='13' />
        <line x1='13' y1='27' x2='8' y2='32' />
      </g>
    </svg>
  );

  // Horizontal layout, longer width than height
  return (
    <div
      style={{
        width: 380,
        height: 140,
        borderRadius: 24,
        boxShadow: '0 4px 20px rgba(0,0,0,0.10)',
        background: 'linear-gradient(135deg, #fffbe6 0%, #f1f8ff 100%)',
        padding: '1.5rem',
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        fontFamily:
          "system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif",
      }}
    >
      {/* Left side: icon, temperature, location */}
      <div
        style={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          minWidth: 120,
        }}
      >
        <div style={{ marginBottom: 8 }}>{SunIcon}</div>
        <div
          style={{
            fontSize: 48,
            fontWeight: 700,
            color: '#222',
            lineHeight: '1',
            marginBottom: 4,
          }}
        >
          {weather.temperature}&deg;
        </div>
        <div style={{ fontSize: 16, color: '#888', marginTop: 2 }}>
          {weather.location}
        </div>
      </div>
      {/* Right side: condition, humidity, wind */}
      <div
        style={{
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center',
          flex: 1,
          paddingLeft: 22,
        }}
      >
        <div
          style={{
            fontSize: 21,
            fontWeight: 500,
            color: '#666',
            marginBottom: 8,
          }}
        >
          {weather.condition}
        </div>
        <div style={{ display: 'flex', gap: 32 }}>
          <div style={{ textAlign: 'center' }}>
            <div style={{ fontSize: 12, color: '#aaa' }}>Humidity</div>
            <div style={{ fontSize: 15, fontWeight: 500, color: '#333' }}>
              {weather.humidity}%
            </div>
          </div>
          <div style={{ textAlign: 'center' }}>
            <div style={{ fontSize: 12, color: '#aaa' }}>Wind</div>
            <div style={{ fontSize: 15, fontWeight: 500, color: '#333' }}>
              {weather.wind} mph
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default WeatherWidget;
