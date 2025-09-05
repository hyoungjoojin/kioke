import { cn } from '@/lib/utils';

interface BaseLayoutProps {
  header: React.ReactNode;
  main: React.ReactNode;
}

export default function BaseLayout({ header, main }: BaseLayoutProps) {
  return (
    <div
      className={cn(
        'flex flex-col h-full',
        'px-5 md:px-15 lg:px-20 2xl:px-36',
        'py-5 md:py-10 lg:py-15 2xl:py-18',
      )}
    >
      <header className={'h-12 md:h-15 lg:h-20'}>{header}</header>
      <main className='h-full pt-5'>{main}</main>
    </div>
  );
}
