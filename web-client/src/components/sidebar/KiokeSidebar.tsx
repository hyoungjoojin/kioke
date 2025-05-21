'use client';

import ProfileButton from './components/ProfileButton';
import { Button } from '@/components/ui/button';
import {
  Collapsible,
  CollapsibleContent,
  CollapsibleTrigger,
} from '@/components/ui/collapsible';
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  useSidebar,
} from '@/components/ui/sidebar';
import { Skeleton } from '@/components/ui/skeleton';
import Icon, { IconName } from '@/components/utils/icon';
import { KIOKE_ROUTES } from '@/constants/route';
import View from '@/constants/view';
import { useShelvesQuery } from '@/hooks/query/shelf';
import { useSelectedShelfId } from '@/hooks/store/shelf';
import { useCurrentView } from '@/hooks/store/view';
import { cn } from '@/lib/utils';
import { User } from 'next-auth';
import { useRouter } from 'next/navigation';

const SIDEBAR_MENU_ITEMS = [
  {
    label: 'Home',
    icon: null,
    view: View.HOME,
  },
  {
    label: 'Bookmarks',
    icon: null,
    view: View.BOOKMARKS,
  },
];

export default function KiokeSidebar({ user }: { user: User }) {
  const router = useRouter();

  const { data: shelves, isLoading } = useShelvesQuery();
  const { selectedShelfId } = useSelectedShelfId();

  const { setOpen } = useSidebar();

  const { currentView, setCurrentView } = useCurrentView();

  return (
    <Sidebar className='pt-3 pl-3'>
      <SidebarHeader className='mb-5 pr-1'>
        <div className='w-full flex justify-between items-center'>
          <ProfileButton firstName={user.firstName} lastName={user.lastName} />

          <div>
            <Button variant='ghost' className='hover:cursor-not-allowed'>
              <Icon name={IconName.NOTIFICATIONS} />
            </Button>
            <Button
              variant='ghost'
              onClick={() => {
                setOpen(false);
              }}
            >
              <Icon name={IconName.MENU} />
            </Button>
          </div>
        </div>
      </SidebarHeader>

      <SidebarContent className='pr-3'>
        <SidebarGroup className='mb-5'>
          <SidebarMenu>
            {SIDEBAR_MENU_ITEMS.map((sidebarMenuItem, index) => {
              return (
                <SidebarMenuItem key={index}>
                  <SidebarMenuButton asChild>
                    <Button
                      variant='ghost'
                      className={cn(
                        'justify-start',
                        currentView === sidebarMenuItem.view
                          ? 'bg-gray-200 hover:bg-gray-200'
                          : '',
                      )}
                      onClick={() => {
                        if (currentView === sidebarMenuItem.view) {
                          return;
                        }

                        router.push(KIOKE_ROUTES[sidebarMenuItem.view]());
                        setCurrentView(sidebarMenuItem.view);
                      }}
                    >
                      {sidebarMenuItem.icon}
                      <span>{sidebarMenuItem.label}</span>
                    </Button>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              );
            })}
          </SidebarMenu>
        </SidebarGroup>

        <Collapsible defaultOpen className='group/collapsible'>
          <SidebarGroup>
            <SidebarGroupLabel asChild>
              <CollapsibleTrigger className='hover:bg-sidebar-accent hover:text-sidebar-accent-foreground hover:cursor-not-allowed'>
                <span className='text-sm text-black'>Shelves</span>
              </CollapsibleTrigger>
            </SidebarGroupLabel>

            <CollapsibleContent>
              <SidebarGroup>
                <SidebarGroupContent>
                  <SidebarMenu>
                    {isLoading ? (
                      <>
                        <Skeleton className='w-full h-9' />
                        <Skeleton className='w-full h-9' />
                        <Skeleton className='w-full h-9' />
                      </>
                    ) : (
                      shelves &&
                      shelves.map((shelf, index) => {
                        return (
                          <SidebarMenuItem key={index}>
                            <SidebarMenuButton asChild>
                              <Button
                                variant='ghost'
                                className={cn(
                                  'justify-start',
                                  currentView === View.SHELF &&
                                    shelf.shelfId === selectedShelfId
                                    ? 'bg-gray-200 hover:bg-gray-200'
                                    : '',
                                )}
                                onClick={() => {
                                  router.push(
                                    KIOKE_ROUTES[View.SHELF](shelf.shelfId),
                                  );
                                }}
                              >
                                <span>{shelf.name}</span>
                              </Button>
                            </SidebarMenuButton>
                          </SidebarMenuItem>
                        );
                      })
                    )}
                  </SidebarMenu>
                </SidebarGroupContent>
              </SidebarGroup>
            </CollapsibleContent>
          </SidebarGroup>
        </Collapsible>
      </SidebarContent>
      <SidebarFooter />
    </Sidebar>
  );
}
