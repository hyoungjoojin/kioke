import type { WidgetProps } from '@/components/feature/dashboard/widgets';
import Image from 'next/image';

function JournalCoverWidget({ isPreview }: WidgetProps) {
  const title = isPreview ? 'My Journal' : '';
  const subtitle = isPreview ? 'Daily reflections' : '';

  return (
    <div className='rounded-3xl shadow-xl bg-white flex flex-col relative overflow-hidden'>
      <div className='relative h-full w-full'>
        {isPreview && (
          <Image
            src='/assets/images/cover.jpeg'
            alt={title}
            fill
            priority
            className='object-cover rounded-3xl'
          />
        )}

        <div className='absolute inset-0 rounded-3xl bg-gradient-to-t from-black/60 via-black/10 to-transparent'></div>
        <div className='absolute bottom-0 left-0 w-full px-6 pb-6'>
          <h2 className='text-white text-2xl font-bold drop-shadow-lg'>
            {title}
          </h2>
          <p className='text-white/70 text-sm drop-shadow-sm mt-1'>
            {subtitle}
          </p>
        </div>
      </div>
    </div>
  );
}

export default JournalCoverWidget;
