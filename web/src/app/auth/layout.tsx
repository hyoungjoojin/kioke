import { Card } from '@/components/ui/card';
import { cn } from '@/lib/utils';

export default function AuthLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
      <header>{/* TODO: Insert logo */}</header>
      <main className={cn('flex flex-row h-dvh justify-center items-center')}>
        <Card className='w-[32rem]'>{children}</Card>
      </main>
    </>
  );
}
