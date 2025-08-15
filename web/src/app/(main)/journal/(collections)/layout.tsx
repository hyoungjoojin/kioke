import { cn } from '@/lib/utils';

export default async function JournalCollectionsLayout({
  header,
  main,
}: Readonly<{
  header: React.ReactNode;
  main: React.ReactNode;
}>) {
  return (
    <div
      className={cn(
        'flex flex-col h-full',
        'px-5 md:px-15 lg:px-20 2xl:px-36',
        'py-5 md:py-10 lg:py-15 2xl:py-18',
      )}
    >
      <header className={cn('h-12 md:h-15 lg:h-20')}>{header}</header>
      <main>{main}</main>
    </div>
  );
}
