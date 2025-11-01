import type { WidgetProps } from '@/components/feature/dashboard/widgets';
import { cn } from '@/lib/utils';

export default function WeatherWidget({ disabled }: WidgetProps) {
  return (
    <div
      className={cn(
        'rounded-2xl shadow-lg bg-gradient-to-br from-blue-400 via-blue-300 to-yellow-200 text-gray-900 flex flex-col items-center justify-center w-full max-w-xs transition-all duration-300 hover:scale-[1.03] hover:shadow-2xl',
        disabled && 'pointer-events-none opacity-50',
      )}
      onClick={(e) => {
        if (disabled) {
          e.preventDefault();
        }
      }}
      aria-disabled={disabled}
    >
      <div className='mb-4'>
        <svg
          width='64'
          height='64'
          viewBox='0 0 64 64'
          fill='none'
          xmlns='http://www.w3.org/2000/svg'
        >
          <circle cx='32' cy='32' r='18' fill='#FFD700' />
          <ellipse cx='44' cy='36' rx='12' ry='8' fill='#B3E0FF' />
          <ellipse cx='24' cy='38' rx='10' ry='6' fill='#E0F7FA' />
        </svg>
      </div>
      {/* Weather Data */}
      <div className='text-center'>
        <h2 className='text-xl font-bold mb-1'>72°F</h2>
        <p className='text-sm text-gray-700 mb-4'>San Francisco, CA</p>
      </div>
    </div>
  );
}
