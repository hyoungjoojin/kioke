'use client';

import { cn } from '@/lib/utils';
import * as TabsPrimitive from '@radix-ui/react-tabs';
import * as React from 'react';

type TabsContextProps = {
  variant: 'horizontal' | 'vertical';
};

const TabsContext = React.createContext<TabsContextProps | null>(null);

function useTabsContext() {
  const context = React.useContext(TabsContext);
  if (!context) {
    throw new Error('useTabs must be used within a TabsContext provider.');
  }

  return context;
}

function Tabs({
  className,
  variant = 'horizontal',
  ...props
}: React.ComponentProps<typeof TabsPrimitive.Root> & {
  variant?: 'horizontal' | 'vertical';
}) {
  return (
    <TabsContext.Provider
      value={{
        variant,
      }}
    >
      <TabsPrimitive.Root
        data-slot='tabs'
        className={cn(
          'flex gap-2',
          variant === 'horizontal' && 'flex-col',
          className,
        )}
        {...props}
      />
    </TabsContext.Provider>
  );
}

function TabsList({
  className,
  ...props
}: React.ComponentProps<typeof TabsPrimitive.List>) {
  const { variant } = useTabsContext();

  return (
    <TabsPrimitive.List
      data-slot='tabs-list'
      className={cn(
        'inline-flex items-center justify-center rounded-lg p-[3px]',
        variant === 'vertical' ? 'sm:flex-col h-fit w-fit' : 'h-9 w-fit',
        className,
      )}
      {...props}
    />
  );
}

function TabsTrigger({
  className,
  children,
  ...props
}: React.ComponentProps<typeof TabsPrimitive.Trigger> & {}) {
  const { variant } = useTabsContext();

  return (
    <TabsPrimitive.Trigger
      data-slot='tabs-trigger'
      className={cn(
        'inline-flex items-center justify-between w-full whitespace-nowrap px-3 py-1 text-sm font-medium transition-all focus-visible:outline-none disabled:pointer-events-none disabled:opacity-50  data-[state=active]:text-foreground',
        variant === 'horizontal'
          ? 'data-[state=active]:border-b data-[state=active]:border-b-black'
          : 'border-l border-transparent data-[state=active]:border-l data-[state=active]:border-l-black',
        className,
      )}
      {...props}
    >
      <span className='grow'>{children}</span>
    </TabsPrimitive.Trigger>
  );
}

function TabsContent({
  className,
  ...props
}: React.ComponentProps<typeof TabsPrimitive.Content>) {
  return (
    <TabsPrimitive.Content
      data-slot='tabs-content'
      className={cn('flex-1 outline-none', className)}
      {...props}
    />
  );
}

export { Tabs, TabsList, TabsTrigger, TabsContent };
