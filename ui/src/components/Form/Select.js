import { FormControl, FormHelperText, InputLabel, MenuItem, Select as MuiSelect } from '@material-ui/core'

const Select = ({ field, form, label, options, ...props }) => {
  const error = form.touched[field.name] && Boolean(form.errors[field.name])
  return (
    <FormControl variant="outlined" fullWidth error={error} {...field} {...props}>
      <InputLabel>{label}</InputLabel>
      <MuiSelect label={label} {...field} {...props}>
        {options.map(option =>
          <MenuItem key={option.value} value={option.value}>
            {option.label}
          </MenuItem>
        )}
      </MuiSelect>
      {error && <FormHelperText>{form.errors[field.name]}</FormHelperText>}
    </FormControl>
  )
}

export default Select


