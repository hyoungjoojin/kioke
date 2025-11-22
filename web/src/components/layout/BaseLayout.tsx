import KiokeSidebar, { KiokeSidebarTrigger } from './KiokeSidebar';
import { SIDEBAR_COOKIE_NAME, SidebarProvider } from '@/components/ui/sidebar';
import { cookies } from 'next/headers';

interface BaseLayoutProps {
  children: React.ReactNode;
  topLeft?: React.ReactNode;
  topRight?: React.ReactNode;
}

async function BaseLayout({ children, topLeft, topRight }: BaseLayoutProps) {
  const cookieStore = await cookies();
  const sidebarOpen = cookieStore.get(SIDEBAR_COOKIE_NAME)?.value === 'true';

  return (
    <SidebarProvider className='h-full' defaultOpen={sidebarOpen}>
      <KiokeSidebar />
      <main className='h-full w-full flex flex-col'>
        <div className='p-5 h-20 flex justify-between items-center'>
          <div className='flex items-center gap-4'>
            <div>
              <KiokeSidebarTrigger />
            </div>

            <div>{topLeft}</div>
          </div>

          <div>{topRight}</div>
        </div>

        <div className='py-7 px-15 grow overflow-hidden'>{children}</div>
      </main>
    </SidebarProvider>
  );
}

export default BaseLayout;
