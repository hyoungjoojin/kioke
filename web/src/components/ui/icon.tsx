type IconName = 'location' | 'location-plus';

type IconProps = React.ComponentProps<'svg'> & {
  name: IconName;
  size: number;
};

function Icon({ name, size, className, ...props }: IconProps) {
  return (
    <svg width={size} height={size} className={className} {...props}>
      <use href={`/assets/icons/sprite.svg#${name}`} />
    </svg>
  );
}

export default Icon;
